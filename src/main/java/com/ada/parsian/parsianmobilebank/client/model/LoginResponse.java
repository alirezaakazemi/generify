package com.ada.parsian.parsianmobilebank.client.model;

public class LoginResponse implements IBankResponse {

    private String loginToken;
    private String customerNumber;
    private String sessionId;
    private String code;
    private String foreignName;
    private String gender;
    private String lastLoginTime;
    private String name;
    private String title;
    private boolean requiredChangeSecondPassword;

    public LoginResponse() {
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRequiredChangeSecondPassword() {
        return requiredChangeSecondPassword;
    }

    public void setRequiredChangeSecondPassword(boolean requiredChangeSecondPassword) {
        this.requiredChangeSecondPassword = requiredChangeSecondPassword;
    }
}
