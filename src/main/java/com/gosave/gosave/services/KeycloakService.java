package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.response.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class KeycloakService {

    private final Keycloak keycloak;

    private RealmResource getRealmResource() {
        return keycloak.realm("wallet");
    }

    public AccountResponse createUser(CreateAccountRequest accountRequest) {
//        System.out.println(keycloak.tokenManager().getAccessToken().getToken());
        log.info("Creating account {}", accountRequest.getUsername());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(accountRequest.getUsername());
        user.setEmail(accountRequest.getEmail());
        user.setFirstName(accountRequest.getFirstname());
        user.setLastName(accountRequest.getLastname());
        user.setEmailVerified(true);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(accountRequest.getPassword());
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getRealmResource().users();

//        try(var response = usersResource.create(user)){
//            log.info("Created user {}", response.getEntity());
//        }
        Response response = usersResource.create(user);
        log.info("account {} created", response);

//        if (response.getStatus() == 201) {
//            System.out.println("User created successfully");
//        } else {
//            System.out.println("Failed to create user");
//        }
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setMessage("Register successful");
        return accountResponse;
    }
}
