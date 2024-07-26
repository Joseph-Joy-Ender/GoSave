package com.gosave.gosave.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;

@Setter
@Getter
@Builder
public class ExceptionApiResponse {
    private boolean isSuccessful;
    private Object data;
}
