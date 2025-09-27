package com.UserService.Dto.Request;

import com.UserService.Constants.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreatePasswordRequest {
    @NotBlank(message = Messages.MAIL_REQUIRED)
    @Email(message = Messages.INVALID_MAIL)
    private String email;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain an uppercase, a lowercase, a number, and a special character"
    )
    private String password;
}
