package com.chuwa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "sign up form class")
public class SignupFormDTO {

    @Schema(description = "username")
    @NotNull(message = "username is required")
    private String username;

    @Schema(description = "password")
    @NotNull(message = "password is required")
    private String password;

    @Schema(description = "phone number")
    @NotNull(message = "phone number is required")
    private String phone;
}
