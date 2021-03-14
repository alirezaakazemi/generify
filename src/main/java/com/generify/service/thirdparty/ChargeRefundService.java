package com.generify.service.thirdparty;

import com.generify.AppConfig;
import com.generify.Util.ParsianUtil;
import com.generify.exception.ObjectNotFoundException;
import com.generify.model.Constant;
import com.generify.repository.card.ChargeRepository;
import com.generify.repository.entity.Charge;
import com.generify.thirdparty.charge.model.ChargeWebhookRequest;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class ChargeRefundService {

    AppConfig appConfig;
    ChargeRepository chargeRepository;
    //    ChargeServerIpRepository chargeServerIpRepository;
    MessageSource messageSource;

    public ChargeRefundService(ChargeRepository chargeRepository/*, ChargeServerIpRepository chargeServerIpRepository*/, MessageSource messageSource, AppConfig appConfig) {
        this.chargeRepository = chargeRepository;
//        this.chargeServerIpRepository = chargeServerIpRepository;
        this.messageSource = messageSource;
        this.appConfig = appConfig;
    }

    public ResponseEntity<Object> execute(ChargeWebhookRequest request, HttpServletRequest servletRequest) {

//        if (chargeServerIpRepository.findByIp(servletRequest.getRemoteAddr()) == null) { // Caller of refund has not permission
//            throw new PermissionDeniedException(messageSource, new ArrayList<>());
//        }

        // Get charge by requestId
        Charge charge = chargeRepository.findByRequestId(String.valueOf(request.getRequestId()));

        if (charge == null) {  // Charge not found

            // Throw exception
            throw new ObjectNotFoundException(ParsianUtil.getMessage(messageSource, Constant.CHARGE_NOT_FOUND_KEY),
                    new ArrayList<String>() {{
                        add(Constant.CHARGE_NOT_FOUND_KEY);
                    }});
        }

        // Update charge to refunded
        charge.setRefunded((byte) 1);
        charge = chargeRepository.save(charge);

//        // Call reverse todo(Reverse of payment must be implemented)

        return ResponseEntity.ok().build();
    }
}
