package com.hasanoztunc.controllers;

import com.hasanoztunc.dtos.authentication.AuthenticationRequest;
import com.hasanoztunc.dtos.authentication.AuthenticationResponse;
import com.hasanoztunc.dtos.authentication.RegisterResponse;
import com.hasanoztunc.dtos.base.RootEntity;

public interface AuthenticationController {

    RootEntity<RegisterResponse> register(AuthenticationRequest authenticationRequest);

    RootEntity<AuthenticationResponse> authentication(AuthenticationRequest authenticationRequest);
}
