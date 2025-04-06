package io.github.iprodigy.bttv.ws.internal;

import io.github.iprodigy.bttv.common.Provider;
import io.github.iprodigy.bttv.ws.internal.data.ChannelData;

public final class PartChannelPayload extends Payload<ChannelData> {

    PartChannelPayload() {
        this.name = PayloadType.PART_CHANNEL;
    }

    public PartChannelPayload(ChannelData data) {
        this();
        this.data = data;
    }

    public static PartChannelPayload of(Provider provider, String providerId) {
        return new PartChannelPayload(new ChannelData(provider, providerId));
    }

}
