package com.ada.parsian.parsianmobilebank;

import com.ada.parsian.parsianmobilebank.client.LoginClient;
import com.ada.parsian.parsianmobilebank.client.card.CardClient;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.ChargeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ParsianMobileBankApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {

        SpringApplication.run(ParsianMobileBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        LoginClient.instance.init(appConfig.getBankConfig().getEndpoint());
        CardClient.instance.init(appConfig.getBankConfig().getEndpoint());
        ChargeClient.instance.init(appConfig.getChargeServiceConfig());
    }
}
