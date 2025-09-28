package com.TransactionService.Dto.Response;


import com.TransactionService.Constants.Transaction.Status;
import com.TransactionService.Constants.Transaction.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPaymentResponse {
    private BigDecimal amount;
    private String paymentReference;
    private String transactionReference;
    private Type transactionType;
    private String description;
    private Status status;
    private String failure_reason;
    private LocalDateTime createdAt;


}