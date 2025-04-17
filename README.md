# BTTV4J

[![Latest](https://img.shields.io/github/release/iProdigy/bttv4j/all.svg?style=flate&label=latest)](https://central.sonatype.com/namespace/io.github.iprodigy.bttv)
[![Javadoc](https://javadoc.io/badge2/io.github.iprodigy.bttv/bttv4j-common/javadoc.svg)](https://javadoc.io/doc/io.github.iprodigy.bttv)

Modular Java wrapper for [BetterTTV](https://betterttv.com/)'s [REST API](https://betterttv.com/developers/api) and [WebSocket](https://betterttv.com/developers/websocket) interface.

## REST API

```java
BetterTTV api = BttvApiFactory.build();

// Obtain the global emotes
Collection<Emote> emotes = api.getGlobalEmotes();

// Query the emotes for a given channel
User user = api.getUser(Provider.TWITCH, "11785491");

// Query which users have BTTV badges for a given platform
Collection<BadgedUser> badges = api.getBadges(Provider.TWITCH);
```

## WebSocket

```java
// Establish websocket connection
BttvSocket ws = new BttvSocket();

// Prepare event handlers
ws.getEventManager().onEvent(EmoteCreateEvent.class, System.out::println);
ws.getEventManager().onEvent(EmoteUpdateEvent.class, System.out::println);
ws.getEventManager().onEvent(EmoteDeleteEvent.class, System.out::println);
ws.getEventManager().onEvent(UserUpdateEvent.class, System.out::println);

// Join channels
String channelId = "53888434", userId = "149223493";
ws.joinChannel(Provider.TWITCH, channelId);
ws.broadcastMe(Provider.TWITCH, channelId, userId);
```

## Notice

This project is not affiliated, associated, authorized, endorsed by, or in any way officially connected with BetterTTV or NightDev, LLC. or Twitch Interactive.

All trademarks are property of their respective owners, and this project does not grant any rights to any trademarks.
