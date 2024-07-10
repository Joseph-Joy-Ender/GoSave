package com.gosave.gosave.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Setter
@ToString
public class PayStackTransactionResponse {
    private boolean status;
    private String message;
    @JsonProperty("data")
    private KudaTransactionDetails kudaTransactionDetails;
}
