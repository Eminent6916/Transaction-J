package com.UserService.Dto.Response;

import com.UserService.Constants.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtData {
    private Long id;
    private String email;
    private Status status;
    private Boolean isEmailVerified;

}
