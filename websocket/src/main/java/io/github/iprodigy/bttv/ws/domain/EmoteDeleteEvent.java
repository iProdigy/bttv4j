package io.github.iprodigy.bttv.ws.domain;

/**
 * @param emoteId The ID of the emote that was deleted.
 * @param channel The channel that the emote was deleted from.
 */
public record EmoteDeleteEvent(String emoteId, Channel channel) {
}
