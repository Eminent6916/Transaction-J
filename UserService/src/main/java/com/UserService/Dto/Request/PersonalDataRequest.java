package com.UserService.Dto.Request;

import com.UserService.Constants.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonalDataRequest {
    @NotBlank(message = Messages.MAIL_REQUIRED)
    @Email(message = Messages.INVALID_MAIL)
    private String email;
    @NotBlank(message = Messages.FIRSTNAME_REQUIRED)
    private String firstName;

    @NotBlank(message = Messages.LASTNAME_REQUIRED)
    private String lastName;
    private String middleName;
    @NotBlank(message = Messages.PHONE_MISSING)
    private String phone;

    @NotBlank(message = Messages.COUNTRY_MISSING)
    private String country;

    @NotBlank(message = Messages.ADDRESS)
    private String address;
}
