package com.ada.parsian.parsianmobilebank.controller;

import com.ada.parsian.parsianmobilebank.model.ClientLoginRequest;
import com.ada.parsian.parsianmobilebank.model.ClientLoginResponse;
import com.ada.parsian.parsianmobilebank.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/account")
public class LoginController extends BaseController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/login")
    public ClientLoginResponse login(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientLoginRequest request) {
        return loginService.execute(getHeaders(requestHeaders, servletRequest), request);
    }
}
