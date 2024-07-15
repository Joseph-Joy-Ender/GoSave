package com.gosave.gosave.controller;

import com.gosave.gosave.dto.response.TransactionHistoryResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/GoSave")
public class PaymentServiceController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/transactionHistory")
    ResponseEntity<TransactionHistoryResponse>getTransactionHistory() throws UserException {
        return  new ResponseEntity<>(paymentService.getTransactionHistory(3L), HttpStatus.OK);
    }
}
