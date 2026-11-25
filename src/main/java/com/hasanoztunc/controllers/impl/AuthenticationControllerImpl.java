package com.hasanoztunc.controllers.impl;

import com.hasanoztunc.controllers.AuthenticationController;
import com.hasanoztunc.controllers.impl.base.BaseController;
import com.hasanoztunc.dtos.authentication.AuthenticationRequest;
import com.hasanoztunc.dtos.authentication.AuthenticationResponse;
import com.hasanoztunc.dtos.authentication.RegisterResponse;
import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api")
public class AuthenticationControllerImpl extends BaseController implements AuthenticationController {

    // region Injections

    private final AuthenticationService authenticationService;

    // endregion

    // region Initialization

    public AuthenticationControllerImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // endregion

    // region Register

    @PostMapping("/register")
    @Override
    public RootEntity<RegisterResponse> register(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return ok(
                authenticationService.register(authenticationRequest)
        );
    }

    // endregion

    // region Authentication

    @PostMapping("/authentication")
    @Override
    public RootEntity<AuthenticationResponse> authentication(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return ok(
                authenticationService.authentication(authenticationRequest)
        );
    }


    // endregion
}
