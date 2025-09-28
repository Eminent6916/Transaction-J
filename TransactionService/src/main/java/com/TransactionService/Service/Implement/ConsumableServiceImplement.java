package com.TransactionService.Service.Implement;

import com.TransactionService.Constants.Messages;
import com.TransactionService.Dto.Response.ApiResponse;
import com.TransactionService.Model.Bank;
import com.TransactionService.Model.Country;
import com.TransactionService.Repository.BankRepository;
import com.TransactionService.Repository.CountryRepository;
import com.TransactionService.Service.ConsumableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumableServiceImplement implements ConsumableService {
    @Autowired
    CountryRepository countryRepository;
    BankRepository bankRepository;

    @Override
    public ResponseEntity<ApiResponse<List<Bank>>> getBanks() {
        List<Bank> banks = bankRepository.findByActive(true);
        return ResponseEntity.ok(ApiResponse.success(Messages.SUCCESSFUL,banks));
    }

    @Override
    public ResponseEntity<ApiResponse<List<Country>>> getCountries() {
        List<Country> countries = countryRepository.findByActive(true);
        return ResponseEntity.ok(ApiResponse.success(Messages.SUCCESSFUL,countries));
    }
}
