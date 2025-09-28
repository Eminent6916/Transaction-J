package com.TransactionService.Service.Implement;

import com.TransactionService.Dto.Request.InitiateTransactionRequest;
import com.TransactionService.Dto.Response.ApiResponse;
import com.TransactionService.Dto.Response.TransactionPaymentResponse;
import com.TransactionService.Service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImplement implements PaymentService {
    @Override
    public ResponseEntity<ApiResponse<TransactionPaymentResponse>> initialisePayment(InitiateTransactionRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<TransactionPaymentResponse>>> getTransactions() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<TransactionPaymentResponse>> getTransaction(String ref) {
        return null;
    }
}
