package com.UserService.Dto.Request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreatePinRequest {
    @Column(name = "pin", length = 6, nullable = false)
    private String pin;
    private String email;
}
