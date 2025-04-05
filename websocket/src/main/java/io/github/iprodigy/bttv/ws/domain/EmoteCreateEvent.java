package io.github.iprodigy.bttv.ws.domain;

import io.github.iprodigy.bttv.common.Emote;

/**
 * @param channel The emote that was created.
 * @param emote   The channel that the emote was created in.
 */
public record EmoteCreateEvent(Channel channel, Emote emote) {
}
