package com.gosave.gosave.controller;

import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/v1/GoSave/")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("addMoneyToWalletFromBank")
    public ResponseEntity<TransferResponse> addMoneyToWalletFromBank(@RequestBody AddMoneyRequest addMoneyRequest) {
        return ResponseEntity.ok(walletService.addMoneyToWalletFromBank(addMoneyRequest));
    }


    @GetMapping("getBalance/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long walletId) {
        System.out.println(walletId);
        return ResponseEntity.ok(walletService.getBalance(walletId));

    }

}
