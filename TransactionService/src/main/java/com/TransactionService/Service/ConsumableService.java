package com.TransactionService.Service;

import com.TransactionService.Dto.Response.ApiResponse;
import com.TransactionService.Model.Bank;
import com.TransactionService.Model.Country;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface  ConsumableService  {
     ResponseEntity<ApiResponse<List<Bank>>> getBanks();
     ResponseEntity<ApiResponse<List<Country>>> getCountries() ;
}
