package io.github.iprodigy.bttv.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BadgeType {

    /**
     * Developer
     */
    DEVELOPER,

    /**
     * NightDev Support Volunteer
     */
    VOLUNTEER,

    /**
     * BetterTTV Emote Approver
     */
    APPROVER,

    /**
     * BetterTTV Translator
     */
    TRANSLATOR;

    @JsonValue
    int getValue() {
        return this.ordinal() + 1;
    }

}
