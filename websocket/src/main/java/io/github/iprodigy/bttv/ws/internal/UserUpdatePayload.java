package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.ws.domain.UserUpdateEvent;

public final class UserUpdatePayload extends Payload<UserUpdateEvent> {
    UserUpdatePayload() {
        this.name = PayloadType.LOOKUP_USER;
    }
}
