package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    private long userId;
    @NotEmpty
    @Size(min = 3, max = 20, message = "user name must be between 3 to 20 character")
    private String username;
    @NotEmpty
    @Email(message = "Email is not valid...")
    private String email;
    @NotEmpty
    private String profileImageUrl;
    @NotEmpty
    @Size(min = 6, max = 20, message = "password must be between 6 to 20 character")
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotEmpty
    private String role;
    private boolean isNotLocked;
}
