package com.kireally.Currency.web.dto.auth;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class JwtRequest {
    @NotNull(message = "Email must not be null!")
    private String email;
    @NotNull(message = "Password must not be null!")
    private String password;
}
