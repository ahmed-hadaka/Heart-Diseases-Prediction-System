package com.graduationproject.heartdiseasepredictionsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDTO {

    @Email(message = "Email is Invalid")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain like .com or .net"
    )
    private String email;

    private String password;
}
