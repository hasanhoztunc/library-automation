package com.hasanoztunc.dtos.authentication;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotEmpty
    @Size(min = 8, max = 40)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 40)
    private String password;
}
