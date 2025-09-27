package com.UserService.Dto.Request;

import com.UserService.Constants.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyIdentityRequest {
    @NotBlank(message = Messages.MAIL_REQUIRED)
    @Email(message = Messages.INVALID_MAIL)
    private String email;

    /**
     * BVN field is optional (nullable).
     * If provided (not null), it must exactly match the pattern of 11 digits.
     * The regex "^\\d{11}$" enforces the 11-digit constraint.
     */
    @Pattern(regexp = "^\\d{11}$", message = Messages.INVALID_BVN)
    private String bvn;

    private String identity;
    private String identityType;
}
