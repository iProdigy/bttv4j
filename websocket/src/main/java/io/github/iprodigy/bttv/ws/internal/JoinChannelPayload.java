package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.ws.internal.data.ChannelData;

public final class JoinChannelPayload extends Payload<ChannelData> {

    JoinChannelPayload() {
        this.name = PayloadType.JOIN_CHANNEL;
    }

    public JoinChannelPayload(ChannelData data) {
        this();
        this.data = data;
    }

    public static JoinChannelPayload of(Provider provider, String providerId) {
        return new JoinChannelPayload(new ChannelData(provider, providerId));
    }

}
