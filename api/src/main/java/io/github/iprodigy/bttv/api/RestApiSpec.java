package io.github.iprodigy.bttv.api;

import java.time.Duration;

/**
 * REST API Specification Customizer
 *
 * @see <a href="https://resilience4j.readme.io/docs/feign">Resilience4J Documentation</a>
 */
public interface RestApiSpec {

    /**
     * @param baseUrl the updated base url
     * @return the same specification instance
     * @apiNote This is only useful when mocking or proxying requests.
     */
    RestApiSpec baseUrl(String baseUrl);

    /**
     * @param rateLimitPoints the number of API calls that can be made within the bucket period
     * @return the same specification instance
     */
    RestApiSpec bucketPoints(int rateLimitPoints);

    /**
     * @param rateLimitPeriod the time period associated with the consumable bucket points
     * @return the same specification instance
     */
    RestApiSpec bucketPeriod(Duration rateLimitPeriod);

    /**
     * @param rateLimitTimeout the sleep period if a rate limit is reached
     * @return the same specification instance
     */
    RestApiSpec bucketTimeout(Duration rateLimitTimeout);

    /**
     * @param minimumNumberOfCalls the number of calls within a sliding window before the breaker percentage is considered
     * @return the same specification instance
     */
    RestApiSpec breakerVolumeThreshold(int minimumNumberOfCalls);

    /**
     * @param failureRateThreshold the percentage of calls that must fail to open the circuit breaker
     * @return the same specification instance
     */
    RestApiSpec breakerFailurePercentage(float failureRateThreshold);

    /**
     * @param waitDurationInOpenState the sleep period if the circuit breaker is opened
     * @return the same specification instance
     */
    RestApiSpec breakerSleepPeriod(Duration waitDurationInOpenState);

    /**
     * @param maxAttempts the maximum number of attempts to make the API call
     * @return the same specification instance
     */
    RestApiSpec retryAttempts(int maxAttempts);

    /**
     * @param waitDuration the sleep period between retry attempts for failed API calls
     * @return the same specification instance
     */
    RestApiSpec retrySpacing(Duration waitDuration);

}
