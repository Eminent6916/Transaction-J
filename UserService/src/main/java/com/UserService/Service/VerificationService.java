package com.UserService.Service;

import com.UserService.Dto.Response.ApiResponse;
import com.UserService.Dto.Response.VerifyMailResponse;
import org.springframework.http.ResponseEntity;

public interface VerificationService {
    ResponseEntity<ApiResponse<VerifyMailResponse>> initiateVerification(String email);
    ResponseEntity<ApiResponse<String>> verifyEmail(String token);
}
