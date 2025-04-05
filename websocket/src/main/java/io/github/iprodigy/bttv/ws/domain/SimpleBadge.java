package io.github.iprodigy.bttv.ws.domain;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

/**
 * @param url       The URL of the user badge.
 * @param startedAt Date when user first subscribed to Pro.
 */
public record SimpleBadge(@NonNull String url, @Nullable Instant startedAt) {
}
