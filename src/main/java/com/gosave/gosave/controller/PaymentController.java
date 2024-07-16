package com.gosave.gosave.controller;

import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.TransactionHistoryResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
 @AllArgsConstructor
@Controller
@RequestMapping("api/v1/GoSave/")
public class PaymentController {

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


    @GetMapping("/transaction-history/{id}")
    public ResponseEntity<TransactionHistoryResponse>getTransactionHistory(@PathVariable Long id) {
        return  new ResponseEntity<>(paymentService.getTransactionHistory(id), HttpStatus.OK);
    }
}

