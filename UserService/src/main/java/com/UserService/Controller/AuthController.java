package com.UserService.Controller;

import com.UserService.Dto.Request.*;
import com.UserService.Dto.Response.LoginResponse;
import com.UserService.Dto.Response.VerifyMailResponse;
import com.UserService.Dto.Response.ApiResponse;
import com.UserService.Service.UserServ;
import com.UserService.Service.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/auth")
@RequiredArgsConstructor
public class AuthController {

    private final VerificationService verificationService;
    private final UserServ userServ;

    @PostMapping("/sendVerification")
    public ResponseEntity<ApiResponse<VerifyMailResponse>> sendVerification(
            @RequestBody @Valid EmailVerificationRequest request) {
//        System.out.println("=== REACHED USER SERVICE ===");
        return verificationService.initiateVerification(request.getEmail());
    }
    @PostMapping("/otpVerification")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestBody @Valid OtpRequest request) {
        return verificationService.verifyEmail(request.getToken());
    }
    @PostMapping("/createPassword")
    public ResponseEntity<ApiResponse<String>> createPassword(@RequestBody @Valid CreatePasswordRequest request){
        return userServ.createPassword(request);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request){
        return userServ.login(request);
    }
    @PostMapping("/personalDetails")
    public ResponseEntity<ApiResponse<Object>> createUserDetails(@RequestBody @Valid PersonalDataRequest request) {
       return userServ.personalDetails(request);
    }
    @PostMapping("/verifyIdentity")
    public ResponseEntity<ApiResponse<Object>> verifyIdentity(@RequestBody @Valid VerifyIdentityRequest request){
        return userServ.verifyIdentity(request);
    }
    @PostMapping("/createPin")
    public ResponseEntity<ApiResponse<Object>> createPin(@RequestBody @Valid CreatePinRequest request){
        return userServ.createPin(request);
    }
}

