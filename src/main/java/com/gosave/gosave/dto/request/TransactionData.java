package com.gosave.gosave.dto.request;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionData {
    private long id;
    private String domain;
    private String status;
    private String reference;
    private int amount;
    private LocalDateTime createdAt;
    private String channel;
    private String currency;
    private int requestedAmount;
}
