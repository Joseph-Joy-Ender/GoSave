package com.gosave.gosave.services;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.dto.request.*;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.dto.response.UserResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;

import java.util.Optional;


public interface AppUserService {

    UserResponse registerUser(UserRequest userRequest);

    SaveResponse save(SaveRequest saveRequest);

    WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException;



}




