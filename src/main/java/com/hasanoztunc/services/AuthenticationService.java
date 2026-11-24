package com.hasanoztunc.services;

import com.hasanoztunc.dtos.authentication.AuthenticationRequest;
import com.hasanoztunc.dtos.authentication.AuthenticationResponse;
import com.hasanoztunc.dtos.authentication.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse register(AuthenticationRequest authenticationRequest);

    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);
}
