package io.github.iprodigy.bttv.api.internal;

import io.github.iprodigy.bttv.api.RestApiSpec;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;
import java.util.Objects;

public final class RestApiSpecImpl implements RestApiSpec {
    private static final String BACKEND_NAME = "bttv4j";

    private String baseUrl = "https://api.betterttv.net/3";
    private RateLimiterConfig rateLimits = RateLimiterConfig.ofDefaults();
    private CircuitBreakerConfig breaker = CircuitBreakerConfig.ofDefaults();
    private RetryConfig retry = RetryConfig.ofDefaults();

    public void validate() {
        if (baseUrl == null || baseUrl.isBlank()) throw new IllegalArgumentException("base url was misspecified");
        Objects.requireNonNull(rateLimits, "rate limiting was misspecified");
        Objects.requireNonNull(breaker, "circuit breaker was misspecified");
        Objects.requireNonNull(retry, "retry policy was misspecified");
    }

    @Override
    public RestApiSpec baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    @Override
    public RestApiSpec bucketPoints(int rateLimitPoints) {
        this.rateLimits = RateLimiterConfig.from(rateLimits).limitForPeriod(rateLimitPoints).build();
        return this;
    }

    @Override
    public RestApiSpec bucketPeriod(Duration rateLimitPeriod) {
        this.rateLimits = RateLimiterConfig.from(rateLimits).limitRefreshPeriod(rateLimitPeriod).build();
        return this;
    }

    @Override
    public RestApiSpec bucketTimeout(Duration rateLimitTimeout) {
        this.rateLimits = RateLimiterConfig.from(rateLimits).timeoutDuration(rateLimitTimeout).build();
        return this;
    }

    @Override
    public RestApiSpec breakerVolumeThreshold(int minimumNumberOfCalls) {
        this.breaker = CircuitBreakerConfig.from(breaker).minimumNumberOfCalls(minimumNumberOfCalls).build();
        return this;
    }

    @Override
    public RestApiSpec breakerFailurePercentage(float failureRateThreshold) {
        this.breaker = CircuitBreakerConfig.from(breaker).failureRateThreshold(failureRateThreshold).build();
        return this;
    }

    @Override
    public RestApiSpec breakerSleepPeriod(Duration waitDurationInOpenState) {
        this.breaker = CircuitBreakerConfig.from(breaker).waitDurationInOpenState(waitDurationInOpenState).build();
        return this;
    }

    @Override
    public RestApiSpec retryAttempts(int maxAttempts) {
        this.retry = RetryConfig.from(retry).maxAttempts(maxAttempts).build();
        return this;
    }

    @Override
    public RestApiSpec retrySpacing(Duration waitDuration) {
        this.retry = RetryConfig.from(retry).waitDuration(waitDuration).build();
        return this;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public RateLimiter createRateLimiter() {
        return RateLimiter.of(BACKEND_NAME, rateLimits);
    }

    public CircuitBreaker createCircuitBreaker() {
        return CircuitBreaker.of(BACKEND_NAME, breaker);
    }

    public Retry createRetry() {
        return Retry.of(BACKEND_NAME, retry);
    }

}
