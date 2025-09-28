package com.TransactionService.Controller;

import com.TransactionService.Dto.Response.ApiResponse;
import com.TransactionService.Model.Bank;
import com.TransactionService.Model.Country;
import com.TransactionService.Service.ConsumableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class ConsumableController {
    private final ConsumableService consumableService;


    @GetMapping("/countries")
    public ResponseEntity<ApiResponse<List<Country>>>getCountries(){
        return consumableService.getCountries();
    }
    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<Bank>>>getBanks(){
        return consumableService.getBanks();
    }

}
