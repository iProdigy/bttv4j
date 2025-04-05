package io.github.iprodigy.bttv.common;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * @param id        ID of the emote.
 * @param code      Name of the emote.
 * @param imageType The image type, e.g. "png", "webp" or "gif".
 * @param animated  Whether the emote is animated.
 * @param userId    The user ID of the emote owner. ("userId" OR "user" provided)
 * @param user      The user ID of the emote owner. ("userId" OR "user" provided)
 */
public record Emote(
        @NonNull String id,
        @NonNull String code,
        @NonNull String imageType,
        boolean animated,
        @Nullable String userId,
        @Nullable EmoteOwner user
) {
}
