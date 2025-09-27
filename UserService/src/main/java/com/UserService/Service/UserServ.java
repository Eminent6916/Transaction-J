package com.UserService.Service;

import com.UserService.Dto.Request.CreatePasswordRequest;
import com.UserService.Dto.Request.LoginRequest;
import com.UserService.Dto.Request.PersonalDataRequest;
import com.UserService.Dto.Request.VerifyIdentityRequest;
import com.UserService.Dto.Response.ApiResponse;
import com.UserService.Dto.Response.LoginResponse;
import com.UserService.Error.UserNotFoundException;
import com.UserService.Model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserServ {
    User fetchByEmail(String email) throws UserNotFoundException;

    ResponseEntity<ApiResponse<String>> createPassword(@Valid CreatePasswordRequest request);

    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);

    ResponseEntity<ApiResponse<Object>> personalDetails(@Valid PersonalDataRequest request);

    ResponseEntity<ApiResponse<Object>> verifyIdentity(@Valid VerifyIdentityRequest request);
}
