package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.ws.domain.EmoteUpdateEvent;

public final class EmoteUpdatePayload extends Payload<EmoteUpdateEvent> {
    EmoteUpdatePayload() {
        this.name = PayloadType.EMOTE_UPDATE;
    }
}
