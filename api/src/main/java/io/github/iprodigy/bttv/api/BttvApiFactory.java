package io.github.iprodigy.bttv.api;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.iprodigy.bttv.common.internal.SharedResources;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import org.jspecify.annotations.NonNull;

public class BttvApiFactory {

    private static final String BACKEND_NAME = "bttv4j";

    private BttvApiFactory() {
        // restrict instantiation
    }

    @NonNull
    public static BetterTTV build() {
        FeignDecorators decorators = FeignDecorators.builder()
                .withRateLimiter(RateLimiter.ofDefaults(BACKEND_NAME))
                .withCircuitBreaker(CircuitBreaker.ofDefaults(BACKEND_NAME))
                .withRetry(Retry.ofDefaults(BACKEND_NAME))
                .build();
        return Feign.builder()
                .addCapability(Resilience4jFeign.capability(decorators))
                .client(new OkHttpClient(SharedResources.HTTP_CLIENT))
                .encoder(new JacksonEncoder(SharedResources.JSON_MAPPER))
                .decoder(new JacksonDecoder(SharedResources.JSON_MAPPER))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .target(BetterTTV.class, "https://api.betterttv.net/3");
    }

}
