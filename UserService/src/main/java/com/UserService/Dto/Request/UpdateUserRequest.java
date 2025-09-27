package com.UserService.Dto.Request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String address;
    private String country;
    private String bvn;
}
