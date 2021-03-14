package com.generify.controller;

import com.generify.model.RequestHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BaseController {

    protected RequestHeaders getHeaders(Map<String, String> headers, HttpServletRequest servletRequest) {

        // Initial requestHeaders
        RequestHeaders requestHeaders = new RequestHeaders();

        // Get headers from incoming request
        requestHeaders.setApplicationVersion(headers.get("application-version"));
        requestHeaders.setClient(headers.get("client"));
        requestHeaders.setClientBrand(headers.get("device-brand"));
        requestHeaders.setClientBrandModel(headers.get("device-model"));
        requestHeaders.setDeviceId(headers.get("device-id"));
        requestHeaders.setMobileNumber(headers.get("mobile-number"));
        requestHeaders.setNetwork(headers.get("network"));
        requestHeaders.setOs(headers.get("os"));
        requestHeaders.setOsVersion(headers.get("os-version"));
        requestHeaders.setCif(headers.get("cif"));
        requestHeaders.setSessionId(headers.get("session-id"));

        if (servletRequest.getHeader("X-Forwarded-For") != null) {
            requestHeaders.setClientIp(servletRequest.getHeader("X-Forwarded-For"));

        } else {
            requestHeaders.setClientIp(servletRequest.getRemoteAddr());
        }

        // Return requestHeaders
        return requestHeaders;
    }
}
