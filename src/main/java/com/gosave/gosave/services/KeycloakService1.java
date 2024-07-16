package com.gosave.gosave.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakService1 {

    private final Keycloak keycloak;


    @PostConstruct
    public void init(){
         log.info("post construct was called. keycloak is ....{} ", keycloak);
//        saveTo();
    }

    public void saveTo(){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername("ned");
        userRepresentation.setEmail("ned@gmail.com");
        userRepresentation.setFirstName("the joy");
        userRepresentation.setLastName("Gosave");
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        log.info("User representation now {}", userRepresentation);

        RealmResource resource = keycloak.realm("wallet");
        resource.users().create(userRepresentation);
    }
}
