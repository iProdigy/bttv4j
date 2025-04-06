package io.github.iprodigy.bttv.ws;

import com.github.philippheuer.events4j.api.IEventManager;
import com.github.philippheuer.events4j.api.service.IEventHandler;
import com.github.philippheuer.events4j.core.EventManager;
import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.common.internal.SharedResources;
import io.github.iprodigy.bttv.ws.domain.Channel;
import io.github.iprodigy.bttv.ws.internal.JoinChannelPayload;
import io.github.iprodigy.bttv.ws.internal.OkSocket;
import io.github.iprodigy.bttv.ws.internal.PartChannelPayload;
import io.github.iprodigy.bttv.ws.internal.Payload;
import io.github.iprodigy.bttv.ws.internal.UserBroadcastPayload;
import io.github.iprodigy.bttv.ws.internal.data.ChannelData;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of BetterTTV's Websocket API.
 * <p>
 * Call {@link BttvSocket#joinChannel(Provider, String)} to receive events associated with the given channel.
 * <p>
 * You may later call {@link BttvSocket#partChannel(Provider, String)} to stop receiving events from any given channel.
 * <p>
 * After joining the initial channels, you should call {@link BttvSocket#broadcastMe(Provider, String, String)}.
 * <p>
 * In addition, you should call {@link BttvSocket#broadcastMe(Provider, String, String)} whenever you
 * programmatically send messages to the channel with BetterTTV emotes.
 * <p>
 * Once channels have been joined, {@link BttvSocket#getEventManager()} can fire:
 * <ul>
 *     <li>{@link io.github.iprodigy.bttv.ws.domain.EmoteCreateEvent}</li>
 *     <li>{@link io.github.iprodigy.bttv.ws.domain.EmoteUpdateEvent}</li>
 *     <li>{@link io.github.iprodigy.bttv.ws.domain.EmoteDeleteEvent}</li>
 *     <li>{@link io.github.iprodigy.bttv.ws.domain.UserUpdateEvent}</li>
 * </ul>
 *
 * @see <a href="https://betterttv.com/developers/websocket">Official Documentation</a>
 */
@SuppressWarnings("unused")
public final class BttvSocket implements AutoCloseable {
    private static final String URL = "wss://sockets.betterttv.net/ws";

    private static final Logger log = LoggerFactory.getLogger(BttvSocket.class);

    @SuppressWarnings("rawtypes")
    private final OkSocket<Payload> socket;

    private final IEventManager eventManager;
    private final ScheduledExecutorService executor;
    private final boolean shouldClose;

    private final Set<ChannelData> channels = Collections.synchronizedSet(new HashSet<>());
    private volatile Channel lastBroadcast = null;

    private BttvSocket(IEventManager eventManager, ScheduledExecutorService executor, boolean shouldClose) {
        this.eventManager = eventManager;
        this.executor = executor;
        this.shouldClose = shouldClose;
        this.socket = new OkSocket<>(SharedResources.HTTP_CLIENT, URL, executor, SharedResources.JSON_MAPPER, Payload.class, this::handleReconnect, payload -> {
            switch (payload.getName()) {
                case EMOTE_CREATE, EMOTE_UPDATE, EMOTE_DELETE, LOOKUP_USER -> eventManager.publish(payload.getData());
                default -> log.warn("Encountered unexpected payload: {}", payload);
            }
        });
    }

    /**
     * Constructs a {@link BttvSocket}.
     *
     * @param eventManager {@link IEventManager}
     * @param executor     {@link ScheduledExecutorService}
     * @apiNote When using this constructor, {@link BttvSocket#close()} does not call {@link ExecutorService#shutdown()} on the passed executor.
     */
    public BttvSocket(@NonNull IEventManager eventManager, @NonNull ScheduledExecutorService executor) {
        this(eventManager, executor, false);
    }

    /**
     * Constructs a {@link BttvSocket}.
     */
    public BttvSocket() {
        this(createEventManager(), Executors.newSingleThreadScheduledExecutor(), true);
    }

    @Override
    public void close() throws Exception {
        socket.close();
        if (shouldClose) {
            executor.shutdown();
            eventManager.close();
        }
    }

    /**
     * Joining a channel will subscribe you to its events, such as emote and user updates.
     *
     * @param provider   The name of the platform.
     * @param providerId Platform user ID for the specified provider.
     * @return whether the join request was sent.
     */
    public boolean joinChannel(Provider provider, String providerId) {
        var data = new ChannelData(provider, providerId);
        channels.add(data);
        return socket.send(new JoinChannelPayload(data));
    }

    /**
     * Parting a channel will unsubscribe you from its events.
     *
     * @param provider   The name of the platform.
     * @param providerId Platform user ID for the specified provider.
     * @return whether the part request was sent.
     */
    public boolean partChannel(Provider provider, String providerId) {
        var data = new ChannelData(provider, providerId);
        channels.remove(data);
        return socket.send(new PartChannelPayload(data));
    }

    /**
     * Broadcasts your user information, if any, to other clients in the same channel.
     * <p>
     * This should be sent when messages are sent by the user.
     *
     * @param provider          The name of the platform.
     * @param providerChannelId Platform channel ID for specified provider.
     * @param providerUserId    Platform user ID for specified provider.
     * @return whether the broadcast message was sent.
     */
    public boolean broadcastMe(Provider provider, String providerChannelId, String providerUserId) {
        this.lastBroadcast = new Channel(provider, providerUserId);
        return socket.send(UserBroadcastPayload.of(provider, providerChannelId, providerUserId));
    }

    /**
     * @return the {@link IEventManager} that will receive BetterTTV events
     */
    public IEventManager getEventManager() {
        return eventManager;
    }

    private void handleReconnect() {
        this.channels.forEach(data -> socket.send(new JoinChannelPayload(data)));
        var lookup = this.lastBroadcast;
        if (lookup != null) {
            broadcastMe(lookup.provider(), lookup.providerId(), lookup.providerId());
        }
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
