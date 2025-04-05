package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.ws.internal.data.ChannelData;
import io.github.iprodigy.bttv.ws.internal.data.UserBroadcastData;

public final class UserBroadcastPayload extends Payload<UserBroadcastData> {

    UserBroadcastPayload() {
        this.name = PayloadType.BROADCAST_ME;
    }

    UserBroadcastPayload(Provider provider, String providerId, ChannelData channel) {
        this();
        this.data = new UserBroadcastData(provider, providerId, channel.name());
    }

    public static UserBroadcastPayload of(Provider provider, String providerChannelId, String providerUserId) {
        return new UserBroadcastPayload(provider, providerUserId, new ChannelData(provider, providerChannelId));
    }

}
