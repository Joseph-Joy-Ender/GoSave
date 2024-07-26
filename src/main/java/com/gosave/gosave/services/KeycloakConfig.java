package com.gosave.gosave.services;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KeycloakConfig {

    @Value("${keycloak.auth.username}")
    private String keycloakUsername;
    @Value("${keycloak.auth.password}")
    private String keycloakPassword;
    @Value("${keycloak-realm}")
    private String keycloakRealm;
    @Value("${keycloak.clientId}")
    private String keycloakClientId;
    @Value("${keycloak.credentials.secret}")
    private String keycloakClientSecret;
    @Value("${keycloak.grant_type}")
    private String grant_type;
    @Value("${keycloak.server.url}")
    private String serverUrl;

    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak keycloak() {
        log.info("........ working, creating keycloak bean -----+++++++");
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(keycloakRealm)
                .grantType(grant_type)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .username(keycloakUsername)
                .password(keycloakPassword)
                .build();
    }
}