package io.github.iprodigy.bttv.api;

import feign.Param;
import feign.RequestLine;
import io.github.iprodigy.bttv.api.domain.BadgedUser;
import io.github.iprodigy.bttv.api.domain.User;
import io.github.iprodigy.bttv.common.Emote;
import io.github.iprodigy.bttv.common.Provider;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;

/**
 * BetterTTV REST API Endpoints
 *
 * @see <a href="https://betterttv.com/developers/api">Official Documentation</a>
 */
@NullMarked
public interface BetterTTV {

    /**
     * Get a User by their provider and provider id.
     *
     * @param provider   The name of the platform.
     * @param providerId Platform user ID for specified provider.
     * @return {@link User}
     */
    @RequestLine("GET /cached/users/{provider}/{providerId}")
    User getUser(@Param("provider") Provider provider, @Param("providerId") String providerId);

    /**
     * @return all BetterTTV's Global Emotes.
     */
    @RequestLine("GET /cached/emotes/global")
    Collection<Emote> getGlobalEmotes();

    /**
     * @param provider The name of the platform.
     * @return users with badges for the specified platform.
     */
    @RequestLine("GET /cached/badges/{provider}")
    Collection<BadgedUser> getBadges(@Param("provider") Provider provider);

}
