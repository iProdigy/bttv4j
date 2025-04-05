package io.github.iprodigy.bttv.ws.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.common.internal.SharedResources;
import io.github.iprodigy.bttv.ws.domain.Channel;
import io.github.iprodigy.bttv.ws.domain.EmoteCreateEvent;
import io.github.iprodigy.bttv.ws.domain.EmoteDeleteEvent;
import io.github.iprodigy.bttv.ws.domain.EmoteUpdateEvent;
import io.github.iprodigy.bttv.ws.domain.UserUpdateEvent;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unittest")
class PayloadTest {
    private final ObjectMapper mapper = SharedResources.JSON_MAPPER;

    @Test
    void serializeJoin() throws JsonProcessingException {
        var payload = JoinChannelPayload.of(Provider.TWITCH, "53888434");
        var json = mapper.writeValueAsString(payload);
        assertEquals("{\"name\":\"join_channel\",\"data\":{\"name\":\"twitch:53888434\"}}", json);
    }

    @Test
    void serializePart() throws JsonProcessingException {
        var payload = PartChannelPayload.of(Provider.TWITCH, "11785491");
        var json = mapper.writeValueAsString(payload);
        assertEquals("{\"name\":\"part_channel\",\"data\":{\"name\":\"twitch:11785491\"}}", json);
    }

    @Test
    void serializeBroadcast() throws JsonProcessingException {
        var payload = UserBroadcastPayload.of(Provider.TWITCH, "91067577", "38974721");
        var json = mapper.writeValueAsString(payload);
        assertEquals("{\"name\":\"broadcast_me\",\"data\":{\"provider\":\"twitch\",\"providerId\":\"38974721\",\"channel\":\"twitch:91067577\"}}", json);
    }

    @Test
    void deserializeCreate() throws JsonProcessingException {
        var payload = mapper.readValue(
                "{\"name\":\"emote_create\",\"data\":{\"emote\":{\"id\":\"5f1b0186cf6d2144653d2970\",\"code\":\"catJAM\",\"imageType\":\"gif\",\"animated\":true,\"user\":{\"id\":\"5f17a10cfe85fb4472d107b4\",\"name\":\"madlittlecat\",\"displayName\":\"MadLittleCat\",\"providerId\":\"36646922\"}},\"channel\":\"twitch:38974721\"}}",
                Payload.class
        );
        assertEquals(PayloadType.EMOTE_CREATE, payload.getName());
        assertInstanceOf(EmoteCreateEvent.class, payload.getData());
        var data = (EmoteCreateEvent) payload.getData();
        assertEquals(new Channel(Provider.TWITCH, "38974721"), data.channel());
        assertEquals("5f1b0186cf6d2144653d2970", data.emote().id());
        assertEquals("catJAM", data.emote().code());
        assertEquals("gif", data.emote().imageType());
        assertTrue(data.emote().animated());
        assertNotNull(data.emote().user());
        assertEquals("5f17a10cfe85fb4472d107b4", data.emote().user().id());
        assertEquals("madlittlecat", data.emote().user().name());
        assertEquals("MadLittleCat", data.emote().user().displayName());
        assertEquals("36646922", data.emote().user().providerId());
    }

    @Test
    void deserializeUpdate() throws JsonProcessingException {
        var payload = mapper.readValue(
                "{\"name\":\"emote_update\",\"data\":{\"emote\":{\"id\":\"5f1b0186cf6d2144653d2970\",\"code\":\"dogJAM\"},\"channel\":\"twitch:38974721\"}}",
                Payload.class
        );
        assertEquals(PayloadType.EMOTE_UPDATE, payload.getName());
        assertInstanceOf(EmoteUpdateEvent.class, payload.getData());
        var data = (EmoteUpdateEvent) payload.getData();
        assertEquals(new Channel(Provider.TWITCH, "38974721"), data.channel());
        assertEquals("5f1b0186cf6d2144653d2970", data.emote().id());
        assertEquals("dogJAM", data.emote().code());
    }

    @Test
    void deserializeDelete() throws JsonProcessingException {
        var payload = mapper.readValue(
                "{\"name\":\"emote_delete\",\"data\":{\"emoteId\":\"5f1b0186cf6d2144653d2970\",\"channel\":\"twitch:38974721\"}}",
                Payload.class
        );
        assertEquals(PayloadType.EMOTE_DELETE, payload.getName());
        assertInstanceOf(EmoteDeleteEvent.class, payload.getData());
        var data = (EmoteDeleteEvent) payload.getData();
        assertEquals("5f1b0186cf6d2144653d2970", data.emoteId());
        assertEquals(new Channel(Provider.TWITCH, "38974721"), data.channel());
    }

    @Test
    void deserializeLookup() throws JsonProcessingException {
        var payload = mapper.readValue(
                "{\"name\":\"lookup_user\",\"data\":{\"name\":\"vasp\",\"providerId\":\"38974721\",\"channel\":\"twitch:91067577\",\"pro\":true,\"emotes\":[{\"id\":\"61f013ba06fd6a9f5be219f3\",\"code\":\"COPIUM\",\"imageType\":\"png\",\"animated\":false,\"user\":{\"id\":\"5ebda868f0fb3f168c4b64e1\",\"name\":\"dzurekk\",\"displayName\":\"Dzurekk\",\"providerId\":\"447870355\"}}],\"badge\":{\"url\":\"https://cdn.betterttv.net/badges/pro/0b58eba8-e49c-4ed7-ae7d-be0b524502e6.png\",\"startedAt\":\"2020-12-01T00:00:00.000Z\"}}}",
                Payload.class
        );
        assertEquals(PayloadType.LOOKUP_USER, payload.getName());
        assertInstanceOf(UserUpdateEvent.class, payload.getData());
        var data = (UserUpdateEvent) payload.getData();
        assertEquals("vasp", data.name());
        assertEquals("38974721", data.providerId());
        assertTrue(data.pro());
        assertNotNull(data.badge());
        assertEquals("https://cdn.betterttv.net/badges/pro/0b58eba8-e49c-4ed7-ae7d-be0b524502e6.png", data.badge().url());
        assertEquals(Instant.parse("2020-12-01T00:00:00.000Z"), data.badge().startedAt());
        assertEquals(1, data.emotes().size());
        var emote = data.emotes().get(0);
        assertEquals("61f013ba06fd6a9f5be219f3", emote.id());
        assertEquals("COPIUM", emote.code());
        assertEquals("png", emote.imageType());
        assertFalse(emote.animated());
        assertNotNull(emote.user());
        assertEquals("5ebda868f0fb3f168c4b64e1", emote.user().id());
        assertEquals("dzurekk", emote.user().name());
        assertEquals("Dzurekk", emote.user().displayName());
        assertEquals("447870355", emote.user().providerId());
    }
}
