package com.TransactionService.Service;

import com.TransactionService.Dto.Request.InitiateTransactionRequest;
import com.TransactionService.Dto.Response.ApiResponse;
import com.TransactionService.Dto.Response.TransactionPaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {

    ResponseEntity<ApiResponse<TransactionPaymentResponse>> initialisePayment(@Valid InitiateTransactionRequest request);

    ResponseEntity<ApiResponse<List<TransactionPaymentResponse>>> getTransactions();

    ResponseEntity<ApiResponse<TransactionPaymentResponse>> getTransaction(String ref);
}
