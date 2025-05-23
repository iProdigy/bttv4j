module bttv4j.common {
    requires com.fasterxml.jackson.annotation;
    requires transitive com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires static kotlin.stdlib; // https://github.com/square/okhttp/issues/8194
    requires static okhttp3; // for SharedResources
    requires static org.jspecify;

    exports io.github.iprodigy.bttv.common;
    exports io.github.iprodigy.bttv.common.internal to bttv4j.api, bttv4j.websocket; // for SharedResources
}
