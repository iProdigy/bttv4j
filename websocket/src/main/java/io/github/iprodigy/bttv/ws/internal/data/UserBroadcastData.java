package io.github.iprodigy.bttv.ws.internal.data;

import io.github.iprodigy.bttv.common.Provider;

/**
 * @param provider   The name of the platform.
 * @param providerId Platform user ID for specified provider.
 * @param channel    The channel that the emote was created in.
 */
public record UserBroadcastData(Provider provider, String providerId, String channel) {
}
