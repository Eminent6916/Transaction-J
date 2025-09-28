package com.TransactionService.Dto.Request;

import com.TransactionService.Constants.Messages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class InitiateTransactionRequest {
    @NotBlank(message = Messages.ACCOUNT_NAME_MISSING)
    private String accountName;

    @NotBlank(message = Messages.ACCOUNT_NUM_MISSING)
    private String accountNumber;

    @NotBlank(message = Messages.BANK_NAME_MISSING)
    private String bankName;

    private String bankSortCode;

    @NotNull(message = Messages.AMOUNT_MISSING)
    @DecimalMin(value = "50.00", inclusive = true, message = "Amount must be greater than 10.00")
    private BigDecimal amount;
    private String transferPurpose;
    @NotNull(message = "Pin required for transaction")
    private String pin;
}
