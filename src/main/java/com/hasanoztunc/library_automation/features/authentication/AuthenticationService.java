package com.hasanoztunc.library_automation.features.authentication;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    GenericResponse<?> registerUser(RegisterDTO registerRequest);

    ResponseEntity<GenericResponse<LoginResponseDTO>> loginUser(LoginDTO loginRequest);

    ResponseEntity<GenericResponse<Void>> logoutUser();
}