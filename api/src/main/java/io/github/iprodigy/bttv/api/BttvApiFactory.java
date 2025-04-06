package io.github.iprodigy.bttv.api;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.iprodigy.bttv.api.internal.RestApiSpecImpl;
import io.github.iprodigy.bttv.common.internal.SharedResources;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

/**
 * Factory to instantiate {@link BetterTTV} endpoint interface.
 */
public final class BttvApiFactory {

    private BttvApiFactory() {
        // restrict instantiation
    }

    /**
     * @param spec specification consumer to customize implementation settings (e.g., retry policy).
     * @return {@link BetterTTV} interface instance with customized settings.
     */
    @NonNull
    public static BetterTTV build(Consumer<RestApiSpec> spec) {
        RestApiSpecImpl specImpl = new RestApiSpecImpl();
        spec.accept(specImpl);
        specImpl.validate();

        FeignDecorators decorators = FeignDecorators.builder()
                .withRateLimiter(specImpl.createRateLimiter())
                .withCircuitBreaker(specImpl.createCircuitBreaker())
                .withRetry(specImpl.createRetry())
                .build();
        return Feign.builder()
                .addCapability(Resilience4jFeign.capability(decorators))
                .client(new OkHttpClient(SharedResources.HTTP_CLIENT))
                .encoder(new JacksonEncoder(SharedResources.JSON_MAPPER))
                .decoder(new JacksonDecoder(SharedResources.JSON_MAPPER))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .target(BetterTTV.class, specImpl.baseUrl());
    }

    /**
     * @return {@link BetterTTV} interface instance with default settings.
     */
    @NonNull
    public static BetterTTV build() {
        return build(spec -> {});
    }

}
