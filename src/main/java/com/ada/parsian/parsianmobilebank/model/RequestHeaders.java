package com.ada.parsian.parsianmobilebank.model;

public class RequestHeaders {

    private String sessionId;
    private String client;
    private String os;
    private String osVersion;
    private String deviceId;
    private String applicationVersion;
    private String clientBrand;
    private String clientBrandModel;
    private String network;
    private String mobileNumber;
    private String cif;
    private String clientIp;

    public RequestHeaders() {
    }

    public RequestHeaders(String sessionId, String client, String os, String osVersion, String deviceId, String applicationVersion, String clientBrand,
                          String clientBrandModel, String network, String mobileNumber, String clientIp) {
        this.sessionId = sessionId;
        this.client = client;
        this.os = os;
        this.osVersion = osVersion;
        this.deviceId = deviceId;
        this.applicationVersion = applicationVersion;
        this.clientBrand = clientBrand;
        this.clientBrandModel = clientBrandModel;
        this.network = network;
        this.mobileNumber = mobileNumber;
        this.clientIp = clientIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getClientBrand() {
        return clientBrand;
    }

    public void setClientBrand(String clientBrand) {
        this.clientBrand = clientBrand;
    }

    public String getClientBrandModel() {
        return clientBrandModel;
    }

    public void setClientBrandModel(String clientBrandModel) {
        this.clientBrandModel = clientBrandModel;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String toString() {
        return "{" +
                "\"client\":\"" + client + "\"," +
                "\"os\":\"" + os + "\"," +
                "\"osVersion\":\"" + osVersion + "\"," +
                "\"deviceId\":\"" + deviceId + "\"," +
                "\"applicationVersion\":\"" + applicationVersion + "\"," +
                "\"clientBrand\":\"" + clientBrand + "\"," +
                "\"clientBrandModel\":\"" + clientBrandModel + "\"," +
                "\"network\":\"" + network + "\"," +
                "\"mobileNumber\":\"" + mobileNumber + "\"," +
                "\"cif\":\"" + cif + "\"," +
                "\"clientIp\":\"" + clientIp + "\"" +
                '}';
    }
}
