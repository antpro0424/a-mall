package com.chuwa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "login form entity")
public class LoginFormDTO {
    @Schema(description = "username")
    @NotNull(message = "username is required")
    private String username;


    @Schema(description = "password")
    @NotNull(message = "password is required")
    private String password;

    @Schema(description = "remember me")
    private Boolean rememberMe = false;
}
