module bttv4j.api {
    requires bttv4j.common;
    requires com.fasterxml.jackson.annotation;
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive com.fasterxml.jackson.datatype.jsr310;
    requires feign.core;
    requires feign.jackson;
    requires feign.okhttp;
    requires feign.slf4j;
    requires io.github.resilience4j.circuitbreaker;
    requires io.github.resilience4j.feign;
    requires io.github.resilience4j.ratelimiter;
    requires io.github.resilience4j.retry;
    requires static org.jspecify;

    exports io.github.iprodigy.bttv.api;
    exports io.github.iprodigy.bttv.api.domain;
}
