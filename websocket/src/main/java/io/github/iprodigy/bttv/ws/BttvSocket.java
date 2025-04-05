package io.github.iprodigy.bttv.ws;

import com.github.philippheuer.events4j.api.IEventManager;
import com.github.philippheuer.events4j.api.service.IEventHandler;
import com.github.philippheuer.events4j.core.EventManager;
import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.common.internal.SharedResources;
import io.github.iprodigy.bttv.ws.internal.JoinChannelPayload;
import io.github.iprodigy.bttv.ws.internal.PartChannelPayload;
import io.github.iprodigy.bttv.ws.internal.Payload;
import io.github.iprodigy.bttv.ws.internal.UserBroadcastPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BttvSocket implements AutoCloseable {
    private static final String URL = "wss://sockets.betterttv.net/ws";

    private static final Logger log = LoggerFactory.getLogger(BttvSocket.class);

    @SuppressWarnings("rawtypes")
    private final OkSocket<Payload> socket;

    private final IEventManager eventManager;

    public BttvSocket(IEventManager eventManager) {
        this.eventManager = eventManager;
        this.socket = new OkSocket<>(SharedResources.HTTP_CLIENT, URL, SharedResources.JSON_MAPPER, Payload.class, payload -> {
            switch (payload.getName()) {
                case EMOTE_CREATE, EMOTE_UPDATE, EMOTE_DELETE, LOOKUP_USER -> eventManager.publish(payload.getData());
                default -> log.warn("Encountered unexpected payload: {}", payload);
            }
        });
    }

    public BttvSocket() {
        this(createEventManager());
    }

    @Override
    public void close() {
        socket.close();
    }

    public boolean joinChannel(Provider provider, String providerId) {
        return socket.send(JoinChannelPayload.of(provider, providerId));
    }

    public boolean partChannel(Provider provider, String providerId) {
        return socket.send(PartChannelPayload.of(provider, providerId));
    }

    public boolean broadcastMe(Provider provider, String providerChannelId, String providerUserId) {
        return socket.send(UserBroadcastPayload.of(provider, providerChannelId, providerUserId));
    }

    public IEventManager getEventManager() {
        return eventManager;
    }

    private static EventManager createEventManager() {
        var em = new EventManager();

        try {
            Class<?> clazz = Class.forName("com.github.philippheuer.events4j.simple.SimpleEventHandler");
            em.registerEventHandler((IEventHandler) clazz.getDeclaredConstructor().newInstance());
            em.setDefaultEventHandler(clazz);
        } catch (Exception e) {
            em.autoDiscovery();
            var handlers = em.getEventHandlers();
            if (handlers.isEmpty()) {
                log.warn("No event handlers found on class path", e);
            } else {
                em.setDefaultEventHandler(handlers.get(0).getClass());
            }
        }

        return em;
    }
}
