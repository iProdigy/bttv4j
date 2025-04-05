package io.github.iprodigy.bttv.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The name of the platform.
 */
public enum Provider {
    TWITCH,
    YOUTUBE;

    public static final Map<String, Provider> MAPPINGS = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(Provider::toString, Function.identity()));

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }
}
