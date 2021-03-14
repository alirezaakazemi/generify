package com.generify;

import oracle.jdbc.pool.OracleDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfig {

    private ChargeServiceConfig chargeServiceConfig;
    private BankConfig bankConfig;
    private EncryptionParam encryptionParam;
    private Spring spring;

    public AppConfig() {
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        // Initial dataSource
        Flyway flyway = new Flyway();
        OracleDataSource dataSource = null;
        try {
            dataSource = new OracleDataSource();
            dataSource.setUser(spring.datasource.username);
            dataSource.setPassword(spring.datasource.password);
            dataSource.setURL(spring.datasource.url);
            flyway
                    .setDataSource(dataSource);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flyway;
    }

    public ChargeServiceConfig getChargeServiceConfig() {
        return chargeServiceConfig;
    }

    public void setChargeServiceConfig(ChargeServiceConfig chargeServiceConfig) {
        this.chargeServiceConfig = chargeServiceConfig;
    }

    public BankConfig getBankConfig() {
        return bankConfig;
    }

    public void setBankConfig(BankConfig bankConfig) {
        this.bankConfig = bankConfig;
    }

    public EncryptionParam getEncryptionParam() {
        return encryptionParam;
    }

    public void setEncryptionParam(EncryptionParam encryptionParam) {
        this.encryptionParam = encryptionParam;
    }

    public Spring getSpring() {
        return spring;
    }

    public void setSpring(Spring spring) {
        this.spring = spring;
    }

    public static class BankConfig {
        private String endpoint;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }
    }

    public static class Spring {
        private Datasource datasource;

        public Datasource getDatasource() {
            return datasource;
        }

        public void setDatasource(Datasource datasource) {
            this.datasource = datasource;
        }
    }

    public static class Datasource {
        private String url;
        private String username;
        private String password;
        private String driverClassName;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }

    public static class ChargeServiceConfig {
        private String endpoint;
        private String username;
        private String password;

        public ChargeServiceConfig() {
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class EncryptionParam {
        private String secretKey;

        public EncryptionParam() {
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }
}
