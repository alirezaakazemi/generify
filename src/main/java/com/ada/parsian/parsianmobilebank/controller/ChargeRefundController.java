package com.ada.parsian.parsianmobilebank.controller;

import com.ada.parsian.parsianmobilebank.service.thirdparty.ChargeRefundService;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.model.ChargeWebhookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/chargeWebhook")
public class ChargeRefundController extends BaseController {

    private final ChargeRefundService chargeRefundService;

    @Autowired
    public ChargeRefundController(ChargeRefundService chargeRefundService) {
        this.chargeRefundService = chargeRefundService;
    }

    @PostMapping(path = "/chargeRefund")
    public ResponseEntity<Object> login(HttpServletRequest servletRequest, @RequestBody ChargeWebhookRequest request) {
        return chargeRefundService.execute(request, servletRequest);
    }
}
