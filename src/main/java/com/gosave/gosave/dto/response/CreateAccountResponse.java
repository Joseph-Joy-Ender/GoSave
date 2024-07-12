package com.gosave.gosave.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
public class CreateAccountResponse {
    private HttpStatus httpStatus;
    private boolean isSuccessful;
    private Object data;
}
