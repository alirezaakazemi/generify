package com.ada.parsian.parsianmobilebank.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ParsianComponent {

    MessageSource messageSource;

    @Autowired
    public ParsianComponent(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
