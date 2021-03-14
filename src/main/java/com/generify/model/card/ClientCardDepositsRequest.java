package com.generify.model.card;

import com.generify.model.ClientBaseRequest;

public class ClientCardDepositsRequest extends ClientBaseRequest implements IClientCardRequest {

    private String cif;
    private String pan;

    public ClientCardDepositsRequest() {
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
