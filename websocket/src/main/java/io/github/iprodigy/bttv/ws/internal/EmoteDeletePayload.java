package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.ws.domain.EmoteDeleteEvent;

public final class EmoteDeletePayload extends Payload<EmoteDeleteEvent> {
    EmoteDeletePayload() {
        this.name = PayloadType.EMOTE_DELETE;
    }
}
