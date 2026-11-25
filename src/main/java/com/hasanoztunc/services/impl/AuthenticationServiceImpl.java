package com.hasanoztunc.services.impl;

import com.hasanoztunc.dtos.authentication.AuthenticationRequest;
import com.hasanoztunc.dtos.authentication.AuthenticationResponse;
import com.hasanoztunc.dtos.authentication.RegisterResponse;
import com.hasanoztunc.enumerations.MessageType;
import com.hasanoztunc.enumerations.Role;
import com.hasanoztunc.exceptions.BaseException;
import com.hasanoztunc.exceptions.ErrorMessage;
import com.hasanoztunc.repositories.UserRepository;
import com.hasanoztunc.security.entities.User;
import com.hasanoztunc.security.jwt.JWTService;
import com.hasanoztunc.services.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    // region Injections

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTService jwtService;

    // endregion

    // region Initialization

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JWTService jwtService
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    // endregion

    // region Register

    @Transactional
    @Override
    public RegisterResponse register(AuthenticationRequest authenticationRequest) {
        if (userRepository.findByUsername(authenticationRequest.getUsername()).isPresent()) {
            throw new BaseException(
                    new ErrorMessage(
                            MessageType.USERNAME_ALREADY_EXISTS,
                            authenticationRequest.getUsername()
                    )
            );
        }

        User savedUser = userRepository.save(createUser(authenticationRequest));

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUsername(savedUser.getUsername());

        return registerResponse;
    }

    // endregion

    // region Authentication

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Optional<User> optionalUser = userRepository.findByUsername(authenticationRequest.getUsername());

            if (optionalUser.isEmpty()) {
                throw new BaseException(
                        new ErrorMessage(
                                MessageType.USERNAME_OR_PASSWORD_INVALID, null
                        )
                );
            }

            User user = optionalUser.get();

            String token = jwtService.generateToken(user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(token);

            return authenticationResponse;
        } catch (Exception exception) {
            throw new BaseException(
                    new ErrorMessage(
                            MessageType.USERNAME_OR_PASSWORD_INVALID, null
                    )
            );
        }
    }


    // endregion

    // region Helpers

    private User createUser(AuthenticationRequest authenticationRequest) {
        User user = new User();

        user.setUsername(authenticationRequest.getUsername());
        user.getRoles().add(Role.USER);

        String encryptedPassword = bCryptPasswordEncoder.encode(authenticationRequest.getPassword());
        user.setPassword(encryptedPassword);

        return user;
    }

    // endregion
}
