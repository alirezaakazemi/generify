package com.generify.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.generify.model.ClientBaseRequest;

public class ClientOwnerRequest extends ClientBaseRequest implements IClientCardRequest {

    @JsonProperty("number")
    private String destinationNumber;

    private String pan;
    private String type;

    public ClientOwnerRequest() {
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
