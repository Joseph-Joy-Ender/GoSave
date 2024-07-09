package com.gosave.gosave.services;


import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.response.SaveResponse;

public interface AppUserService  {
    SaveResponse saveFund (SaveRequest saveRequest);


}
