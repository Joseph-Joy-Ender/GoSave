package com.gosave.gosave.dto.response;
import com.gosave.gosave.dto.request.TransactionData;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TransactionHistoryResponse {
    private boolean status;
    private String  message;
    private List<TransactionData> data;
}
