package com.UserService.Dto.Request;

import com.UserService.Constants.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VerifyEmailRequest {
    @NotBlank(message = Messages.MAIL_REQUIRED)
    @Email(message = Messages.INVALID_MAIL)
    private String email;
}

