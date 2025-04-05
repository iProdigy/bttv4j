package io.github.iprodigy.bttv.ws.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.iprodigy.bttv.common.Provider;

/**
 * @param provider   The name of the platform.
 * @param providerId Platform user Id for specified provider.
 */
public record Channel(Provider provider, String providerId) {
    @JsonCreator
    static Channel of(String serialized) {
        int delim = serialized.indexOf(':');
        var provider = Provider.MAPPINGS.get(serialized.substring(0, delim));
        var id = serialized.substring(delim + 1);
        return new Channel(provider, id);
    }
}
