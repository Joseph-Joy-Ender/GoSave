package com.gosave.gosave.services;

import com.gosave.gosave.dto.response.ApiResponse;

public interface AppUserService {
    ApiResponse<?> transferFunds(Long userId);
}
