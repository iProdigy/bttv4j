package io.github.iprodigy.bttv.common;

import org.jspecify.annotations.NullMarked;

/**
 * @param id          The user ID of the emote owner.
 * @param name        The username of the emote owner.
 * @param displayName The display name of the emote owner.
 * @param providerId  Platform user ID of the emote owner.
 */
@NullMarked
public record EmoteOwner(String id, String name, String displayName, String providerId) {
}
