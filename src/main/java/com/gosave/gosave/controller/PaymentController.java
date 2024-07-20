package com.gosave.gosave.controller;

import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/GoSave/")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/transferFundsToWallet/{userId}")
    public ResponseEntity<ApiResponse> transferFundsToWallet(@PathVariable Long userId) {
        try {
            ApiResponse<?> response = paymentService.transferFundsToWallet(userId);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("getBalance/{walletId}")
    public HttpEntity<WalletResponse> getBalance(@PathVariable Long walletId) {
        System.out.println(walletId);
        return ResponseEntity.ok(paymentService.getBalance(walletId));

    }
}

