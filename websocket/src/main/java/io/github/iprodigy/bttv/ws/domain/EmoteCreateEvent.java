package io.github.iprodigy.bttv.ws.domain;

import io.github.iprodigy.bttv.common.Emote;

/**
 * Create events are sent when a new emote is uploaded to the subscribed channel, or when a new shared emote is added.
 *
 * @param channel The emote that was created.
 * @param emote   The channel that the emote was created in.
 */
public record EmoteCreateEvent(Channel channel, Emote emote) {
}
