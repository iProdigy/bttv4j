package io.github.iprodigy.bttv.ws.domain;

/**
 * This event will be triggered when an emote is deleted from a subscribed channel.
 *
 * @param emoteId The ID of the emote that was deleted.
 * @param channel The channel that the emote was deleted from.
 */
public record EmoteDeleteEvent(String emoteId, Channel channel) {
}
