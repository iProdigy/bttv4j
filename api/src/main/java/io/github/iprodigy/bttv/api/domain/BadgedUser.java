package io.github.iprodigy.bttv.api.domain;

import io.github.iprodigy.bttv.common.Badge;
import org.jspecify.annotations.NullMarked;

/**
 * @param id          ID of user with badge.
 * @param name        Username of user with badge.
 * @param displayName Display name of user with badge.
 * @param providerId  Platform user ID of user with badge.
 * @param badge       Badge assigned to the user.
 */
@NullMarked
public record BadgedUser(String id, String name, String displayName, String providerId, Badge badge) {
}
