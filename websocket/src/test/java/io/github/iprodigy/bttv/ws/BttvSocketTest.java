package io.github.iprodigy.bttv.ws;

import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.ws.domain.EmoteCreateEvent;
import io.github.iprodigy.bttv.ws.domain.EmoteDeleteEvent;
import io.github.iprodigy.bttv.ws.domain.EmoteUpdateEvent;
import io.github.iprodigy.bttv.ws.domain.UserUpdateEvent;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
class BttvSocketTest {
    private final String channelId = "53888434";
    private final String userId = "149223493";

    @Test
    void localTest() throws InterruptedException {
        try (BttvSocket ws = new BttvSocket()) {
            ws.getEventManager().onEvent(EmoteCreateEvent.class, System.out::println);
            ws.getEventManager().onEvent(EmoteUpdateEvent.class, System.out::println);
            ws.getEventManager().onEvent(EmoteDeleteEvent.class, System.out::println);
            ws.getEventManager().onEvent(UserUpdateEvent.class, System.out::println);

            assertTrue(ws.joinChannel(Provider.TWITCH, channelId));
            assertTrue(ws.broadcastMe(Provider.TWITCH, channelId, userId));
            Thread.sleep(Duration.ofMinutes(5L));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
