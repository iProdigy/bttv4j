package io.github.iprodigy.bttv.api;

import io.github.iprodigy.bttv.api.domain.User;
import io.github.iprodigy.bttv.common.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
class RestApiTest {
    private static Logger log;
    private static BetterTTV api;

    @BeforeAll
    static void beforeAll() {
        log = LoggerFactory.getLogger(RestApiTest.class);
        api = BttvApiFactory.build();
    }

    @Test
    void getUser() {
        User user = api.getUser(Provider.TWITCH, "11785491");
        log.trace("getUser response: {}", user);
        assertNotNull(user);
        assertNotNull(user.botNames());
        assertNotNull(user.avatarUrl());
        assertNotNull(user.channelEmotes());
        assertNotNull(user.sharedEmotes());
        user.botNames().forEach(Assertions::assertNotNull);
        user.channelEmotes().forEach(Assertions::assertNotNull);
        user.sharedEmotes().forEach(Assertions::assertNotNull);
    }

    @Test
    void getGlobalEmotes() {
        var emotes = api.getGlobalEmotes();
        log.trace("getGlobalEmotes response: {}", emotes);
        assertNotNull(emotes);
        assertFalse(emotes.isEmpty());
        emotes.forEach(Assertions::assertNotNull);
    }

    @Test
    void getBadgesTwitch() {
        var badges = api.getBadges(Provider.TWITCH);
        log.trace("twitch getBadges response: {}", badges);
        assertNotNull(badges);
        assertFalse(badges.isEmpty());
        badges.forEach(Assertions::assertNotNull);
    }

    @Test
    void getBadgesYouTube() {
        var badges = api.getBadges(Provider.YOUTUBE);
        log.trace("youtube getBadges response: {}", badges);
        assertNotNull(badges);
        assertFalse(badges.isEmpty());
        badges.forEach(Assertions::assertNotNull);
    }
}
