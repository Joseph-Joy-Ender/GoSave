package com.gosave.gosave.controller;

import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/GoSave/")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("addMoneyToWalletFromBank")
    public ResponseEntity<TransferResponse> addMoneyToWalletFromBank(@RequestBody AddMoneyRequest addMoneyRequest) {
        return ResponseEntity.ok(walletService.addMoneyToWalletFromBank(addMoneyRequest));
    }




}
