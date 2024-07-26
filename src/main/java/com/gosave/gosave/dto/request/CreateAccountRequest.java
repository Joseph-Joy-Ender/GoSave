package com.gosave.gosave.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountRequest {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String username;


}
