package io.github.iprodigy.bttv.ws.internal;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PayloadType {
    JOIN_CHANNEL,
    PART_CHANNEL,
    EMOTE_CREATE,
    EMOTE_UPDATE,
    EMOTE_DELETE,
    BROADCAST_ME,
    LOOKUP_USER;

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }
}
