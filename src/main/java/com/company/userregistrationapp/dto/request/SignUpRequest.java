package com.company.userregistrationapp.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    @NotBlank(message = "Username should not be null")
    String username;

    @NotBlank(message = "Mail should not be null")
    @Email(message = "This field should be mail")
    String email;

    @NotBlank(message = "Password should not be null")
    @Size(min = 8,message = "Password should be contains at least 8 characters")
    String password;

    @NotBlank(message = "Confirm password should not be null")
    @Size(min = 8,message = "Confirm Password should be contains at least 8 characters")
    String confirmPassword;
}
