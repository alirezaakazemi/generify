package com.generify.model;

public class ClientBaseRequest implements IClientRequest {
    private String eauth;

    public ClientBaseRequest() {
    }

    public String getEauth() {
        return eauth;
    }

    public void setEauth(String eauth) {
        this.eauth = eauth;
    }
}
