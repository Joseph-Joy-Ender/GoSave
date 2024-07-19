package com.gosave.gosave.controller;

import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.response.AccountResponse;
import com.gosave.gosave.services.KeycloakService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/keycloakAccount")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final KeycloakService keycloakService;

    @PostMapping("/signUp")
    public ResponseEntity<AccountResponse> signUp(@RequestBody CreateAccountRequest accountRequest){
      log.info("Sign up api called .........{}",accountRequest);
       return ResponseEntity.ok(keycloakService.createUser(accountRequest));
    }

}
