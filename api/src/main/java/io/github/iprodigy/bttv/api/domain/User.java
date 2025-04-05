package io.github.iprodigy.bttv.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.iprodigy.bttv.common.Emote;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

/**
 * @param id            The user ID.
 * @param botNames      The usernames which BetterTTV renders with a bot badge.
 * @param avatarUrl     The user avatar.
 * @param channelEmotes A list of channel emotes.
 * @param sharedEmotes  A list of shared emotes.
 */
@NullMarked
public record User(
        String id,
        @JsonProperty("bots") Set<String> botNames,
        @JsonProperty("avatar") String avatarUrl,
        List<Emote> channelEmotes,
        List<Emote> sharedEmotes
) {
}
