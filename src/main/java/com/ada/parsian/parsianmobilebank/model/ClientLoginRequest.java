package com.ada.parsian.parsianmobilebank.model;

public class ClientLoginRequest implements IClientRequest {

    private String localPassword;
    private String username;
    private String password;

    public ClientLoginRequest() {
    }

    public String getLocalPassword() {
        return localPassword;
    }

    public void setLocalPassword(String localPassword) {
        this.localPassword = localPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
