package com.generify.controller;

import com.generify.model.ClientLoginRequest;
import com.generify.model.ClientLoginResponse;
import com.generify.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    private final LoginService loginService;

    @Autowired
    public UserController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/login")
    public ClientLoginResponse login(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientLoginRequest request) {
        return loginService.execute(getHeaders(requestHeaders, servletRequest), request);
    }
}
