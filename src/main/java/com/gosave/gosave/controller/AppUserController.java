package com.gosave.gosave.controller;

import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import com.gosave.gosave.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
 @AllArgsConstructor
//@RestController
@Controller
@RequestMapping("/api/v1/GoSave")
public class AppUserController {
//    @Autowired
    private AppUserService appUserService;

    @PostMapping("/createWallet")
    public ResponseEntity<WalletResponse> createWallet(@RequestBody WalletRequest walletRequest) throws WalletExistException {
       return ResponseEntity.ok(appUserService.createWallet(walletRequest));
    }

}
