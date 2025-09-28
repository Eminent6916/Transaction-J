package com.TransactionService.Controller;

import com.TransactionService.Dto.Request.InitiateTransactionRequest;
import com.TransactionService.Dto.Response.ApiResponse;
import com.TransactionService.Dto.Response.TransactionPaymentResponse;
import com.TransactionService.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class TransactionController {

    private final PaymentService paymentService;
    @PostMapping("")
    public ResponseEntity<ApiResponse<TransactionPaymentResponse>> initialisePayment(@RequestBody @Valid InitiateTransactionRequest request) {
        return paymentService.initialisePayment(request);
    }

    @GetMapping("/{ref}")
    public ResponseEntity<ApiResponse<TransactionPaymentResponse>> getTransaction(@PathVariable String ref) {
        return paymentService.getTransaction(ref);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<TransactionPaymentResponse>>> getTransactions() {
        return paymentService.getTransactions();
    }


}
