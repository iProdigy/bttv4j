# BTTV4J

Modular Java wrapper for the [BetterTTV API](https://betterttv.com/developers/api) and [WebSocket](https://betterttv.com/developers/websocket).

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
