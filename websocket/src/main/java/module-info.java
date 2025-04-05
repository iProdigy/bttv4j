module bttv4j.websocket {
    requires bttv4j.common;
    requires transitive com.fasterxml.jackson.annotation;
    requires transitive com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires transitive com.fasterxml.jackson.datatype.jsr310;
    requires events4j.api;
    requires events4j.core;
    requires static events4j.handler.simple;
    requires okhttp3;
    requires static org.jspecify;
    requires org.slf4j;

    exports io.github.iprodigy.bttv.ws;
    exports io.github.iprodigy.bttv.ws.domain;
}
