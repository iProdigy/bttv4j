package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.ws.domain.EmoteCreateEvent;

public final class EmoteCreatePayload extends Payload<EmoteCreateEvent> {
    EmoteCreatePayload() {
        this.name = PayloadType.EMOTE_CREATE;
    }
}
