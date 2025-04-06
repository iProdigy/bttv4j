package io.github.iprodigy.bttv.api;

import java.time.Duration;

public interface RestApiSpec {

    RestApiSpec baseUrl(String baseUrl);

    RestApiSpec bucketPoints(int rateLimitPoints);

    RestApiSpec bucketPeriod(Duration rateLimitPeriod);

    RestApiSpec bucketTimeout(Duration rateLimitTimeout);

    RestApiSpec breakerVolumeThreshold(int minimumNumberOfCalls);

    RestApiSpec breakerFailurePercentage(float failureRateThreshold);

    RestApiSpec breakerSleepPeriod(Duration waitDurationInOpenState);

    RestApiSpec retryAttempts(int maxAttempts);

    RestApiSpec retrySpacing(Duration waitDuration);

}
