package io.github.iprodigy.bttv.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NullMarked;

/**
 * @param description Description of the badge.
 * @param url         URL of the badge as an SVG file.
 * @param type        Type of the badge.
 */
@NullMarked
public record Badge(String description, @JsonProperty("svg") String url, BadgeType type) {
}
