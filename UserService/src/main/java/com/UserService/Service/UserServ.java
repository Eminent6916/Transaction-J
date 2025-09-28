package com.UserService.Service;

import com.UserService.Dto.Request.*;
import com.UserService.Dto.Response.ApiResponse;
import com.UserService.Dto.Response.LoginResponse;
import com.UserService.Model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface UserServ {
    Optional<User> fetchByEmail(String email) ;

    ResponseEntity<ApiResponse<String>> createPassword(@Valid CreatePasswordRequest request);

    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);

    ResponseEntity<ApiResponse<Object>> personalDetails(@Valid PersonalDataRequest request);

    ResponseEntity<ApiResponse<Object>> verifyIdentity(@Valid VerifyIdentityRequest request);

    ResponseEntity<ApiResponse<Object>> createPin(@Valid CreatePinRequest request);

}
