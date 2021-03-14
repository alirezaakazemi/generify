package com.generify;

import com.generify.client.LoginClient;
import com.generify.client.card.CardClient;
import com.generify.thirdparty.charge.client.ChargeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GenerifyApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {

        SpringApplication.run(GenerifyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        LoginClient.instance.init(appConfig.getBankConfig().getEndpoint());
        CardClient.instance.init(appConfig.getBankConfig().getEndpoint());
        ChargeClient.instance.init(appConfig.getChargeServiceConfig());
    }
}
