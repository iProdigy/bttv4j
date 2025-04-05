package io.github.iprodigy.bttv.ws.internal.data;

import io.github.iprodigy.bttv.common.Provider;

public record ChannelData(String name) {
    public ChannelData(Provider provider, String providerId) {
        this(provider.toString() + ':' + providerId);
    }
}
