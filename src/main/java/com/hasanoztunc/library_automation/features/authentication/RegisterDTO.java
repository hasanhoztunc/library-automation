package com.hasanoztunc.library_automation.features.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterDTO {
    private String username;
    private String fullName;
    private String email;
    private String password;
}