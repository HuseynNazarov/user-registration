package com.company.userregistrationapp.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {

    @NotBlank(message = "Username or mail should not be null")
    String username;

    @NotBlank(message = "Password should not be null")
    @Size(min = 8,message = "Password should be contains at least 8 characters")
    String password;
}
