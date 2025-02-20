package com.gosave.gosave.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ExceptionApiResponse {
    private boolean isSuccessful;
    private Object data;
}
