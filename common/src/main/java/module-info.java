module bttv4j.common {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires static okhttp3; // for SharedResources
    requires static org.jspecify;

    exports io.github.iprodigy.bttv.common;
    exports io.github.iprodigy.bttv.common.internal to bttv4j.api, bttv4j.websocket; // for SharedResources
}
