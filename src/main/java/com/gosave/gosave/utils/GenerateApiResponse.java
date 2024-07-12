package com.gosave.gosave.utils;

import com.gosave.gosave.dto.response.CreateAccountResponse;
import org.springframework.http.HttpStatus;

public class GenerateApiResponse {
    public static final String ACCOUNT_WITH_THIS_EMAIL_ALREADY_EXIST = "Account with this email already exist";
    public static final String REGISTER_SUCCESSFUL = "Register successful";

    public static CreateAccountResponse create(Object data) {
        return CreateAccountResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.CREATED)
                .isSuccessful(true)
                .build();
    }
}
