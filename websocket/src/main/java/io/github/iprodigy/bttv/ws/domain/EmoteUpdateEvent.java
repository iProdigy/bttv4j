package io.github.iprodigy.bttv.ws.domain;

/**
 * @param emote   The emote that was updated.
 * @param channel The channel that the emote was created in.
 */
public record EmoteUpdateEvent(SimpleEmote emote, Channel channel) {
}
