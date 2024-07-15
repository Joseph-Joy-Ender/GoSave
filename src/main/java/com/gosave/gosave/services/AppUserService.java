package com.gosave.gosave.services;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.dto.request.*;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;

import java.util.Optional;


public interface AppUserService {

    SaveResponse save(SaveRequest saveRequest);

    WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException;
    Optional<User> findUser (Long id);


}




