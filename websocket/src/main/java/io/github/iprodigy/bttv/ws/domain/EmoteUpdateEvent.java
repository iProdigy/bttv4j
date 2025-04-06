package io.github.iprodigy.bttv.ws.domain;

/**
 * This event will be triggered when an emote's code is modified.
 * In addition, this event will be emitted when a shared emote's code-alias is updated.
 *
 * @param emote   The emote that was updated.
 * @param channel The channel that the emote was created in.
 */
public record EmoteUpdateEvent(SimpleEmote emote, Channel channel) {
}
