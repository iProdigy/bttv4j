package io.github.iprodigy.bttv.ws.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class OkSocket<D> implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(OkSocket.class);
    private final ObjectMapper mapper;
    private final OkHttpClient client;
    private final Request request;
    private final WebSocketListener listener;
    private final AtomicReference<WebSocket> webSocket = new AtomicReference<>();
    private volatile boolean closed = false;

    public OkSocket(OkHttpClient client, String url, ScheduledExecutorService executor, ObjectMapper mapper, Class<D> payloadClass, Runnable connected, Consumer<D> consumer) {
        this.mapper = mapper;
        this.client = client;
        this.request = new Request.Builder().url(url).build();
        this.listener = new WebSocketListener() {
            @Override
            public void onMessage(@NonNull WebSocket ws, @NonNull String text) {
                log.trace("Received websocket message: {}", text);

                final D d;
                try {
                    d = mapper.readValue(text, payloadClass);
                } catch (JsonProcessingException e) {
                    log.warn("Failed to deserialize websocket payload: {}", text, e);
                    return;
                }

                executor.execute(() -> consumer.accept(d));
            }

            @Override
            public void onFailure(@NonNull WebSocket ws, @NonNull Throwable t, @Nullable Response response) {
                log.info("WebSocket connection failed (will retry shortly): {}", extractResponse(response), t);

                // clean up existing resources
                try {
                    ws.cancel();
                } finally {
                    if (response != null && response.body() != null) {
                        response.close();
                    }
                }

                // prepare new socket
                if (webSocket.compareAndSet(ws, null)) {
                    executor.schedule(OkSocket.this::createSocket, 10L, TimeUnit.SECONDS);
                }
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                executor.execute(connected);
            }
        };
        this.createSocket();
    }

    @Override
    public void close() {
        if (closed) return;

        WebSocket ws;
        while ((ws = webSocket.get()) == null) {
            // busy wait for createSocket to complete since ws would otherwise not be null (unless close was called)
            if (closed)
                return;
        }

        ws.cancel();
        webSocket.lazySet(null);
        closed = true;
    }

    public boolean send(String payload) {
        return webSocket.get().send(payload);
    }

    public boolean send(Object payload) {
        try {
            return send(mapper.writeValueAsString(payload));
        } catch (JsonProcessingException e) {
            log.warn("Failed to serialize outbound payload: {}", payload, e);
            return false;
        }
    }

    private void createSocket() {
        var old = webSocket.getAndSet(client.newWebSocket(request, listener));
        if (old != null) {
            old.cancel();
        }
    }

    private static String extractResponse(Response response) {
        if (response == null) return "did not receive server response";
        if (response.body() == null) return String.valueOf(response.code());
        try {
            return response.body().string();
        } catch (IOException e) {
            log.debug("Failed to extract response body", e);
            return "could not read body for code " + response.code();
        }
    }
}
