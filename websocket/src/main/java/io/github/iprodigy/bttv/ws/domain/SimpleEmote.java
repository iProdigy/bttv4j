package io.github.iprodigy.bttv.ws.domain;

/**
 * @param id   The ID of the emote that was updated.
 * @param code The new code of the emote.
 */
public record SimpleEmote(String id, String code) {
}
