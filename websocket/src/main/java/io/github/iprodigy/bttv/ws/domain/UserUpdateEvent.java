package io.github.iprodigy.bttv.ws.domain;

import io.github.iprodigy.bttv.common.Emote;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @param name       User's platform username.
 * @param providerId User's platform user id.
 * @param channel    The channel the change occurred in.
 * @param pro        Whether the user is a pro subscriber.
 * @param emotes     The user's personal emotes.
 * @param badge      The user's badge.
 */
@NullMarked
public record UserUpdateEvent(
        String name,
        String providerId,
        Channel channel,
        boolean pro,
        List<Emote> emotes,
        @Nullable SimpleBadge badge
) {
}
