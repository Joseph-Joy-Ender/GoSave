package com.gosave.gosave.services;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    private static final Logger log = LoggerFactory.getLogger(KeycloakConfig.class);
    @Value("${keycloak.auth.username}")
    private String keycloakUsername;
    @Value("${keycloak.auth.password}")
    private String keycloakPassword;
    @Value("${realm}")
    private String keycloakRealm;
    @Value("${keycloak.clientId}")
    private String keycloakClientId;
    private String keycloakClientSecret;
    private String grant_type;

    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
    @Bean
    public Keycloak keycloak() {
        log.info("........ working, creating keycloak bean -----+++++++");
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("wallet")
                .clientId("wallet-rest-api")
                .grantType("password")
                .username("admin")
                .password("admin")
                .build();
    }
}