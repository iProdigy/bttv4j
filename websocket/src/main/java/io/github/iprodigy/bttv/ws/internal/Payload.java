package io.github.iprodigy.bttv.ws.internal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name", include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmoteCreatePayload.class, name = "emote_create"),
        @JsonSubTypes.Type(value = EmoteUpdatePayload.class, name = "emote_update"),
        @JsonSubTypes.Type(value = EmoteDeletePayload.class, name = "emote_delete"),
        @JsonSubTypes.Type(value = UserUpdatePayload.class, name = "lookup_user"),
        @JsonSubTypes.Type(value = JoinChannelPayload.class, name = "join_channel"),
        @JsonSubTypes.Type(value = PartChannelPayload.class, name = "part_channel"),
        @JsonSubTypes.Type(value = UserBroadcastPayload.class, name = "broadcast_me")
})
public class Payload<D> {
    protected PayloadType name;
    protected D data;

    Payload() {
        // no args constructor for Jackson
    }

    public Payload(PayloadType name, D data) {
        this.name = name;
        this.data = data;
    }

    public PayloadType getName() {
        return name;
    }

    void setName(PayloadType name) {
        this.name = name;
    }

    public D getData() {
        return data;
    }

    void setData(D data) {
        this.data = data;
    }
}
