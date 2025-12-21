package com.hasanoztunc.library_automation.features.authentication;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.role.MemberRole;
import com.hasanoztunc.library_automation.features.role.Role;
import com.hasanoztunc.library_automation.security.jwt.JwtUtilities;
import com.hasanoztunc.library_automation.security.services.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
            MemberRepository memberRepository,
            AuthenticationManager authenticationManager,
            JwtUtilities jwtUtilities,
            PasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtilities = jwtUtilities;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public GenericResponse<?> registerUser(RegisterDTO registerRequest) {
        if (memberRepository.existsByUsername(registerRequest.getUsername())) {
            return GenericResponse.fail("Error: Username is already taken!");
        }

        if (memberRepository.existsByEmail(registerRequest.getEmail())) {
            return GenericResponse.fail("Error: Email is already taken!");
        }

        var member = new Member(
                registerRequest.getUsername(),
                registerRequest.getFullName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();
        var memberRole = new Role(MemberRole.ROLE_MEMBER);
        roles.add(memberRole);

        member.setRoles(roles);

        memberRepository.save(member);

        return GenericResponse
                .success("User registered successfully!");
    }

    @Override
    public ResponseEntity<GenericResponse<LoginResponseDTO>> loginUser(LoginDTO loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(
                    GenericResponse.fail("Error: Invalid username or password!"),
                    HttpStatus.UNAUTHORIZED
            );
        }

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var jwtCookie = jwtUtilities.generateJwtCookie(userDetails);

        var loginResponse = new LoginResponseDTO(
                userDetails.getUsername(),
                userDetails.getFullName(),
                userDetails.getEmail()
        );

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(GenericResponse.success(loginResponse));
    }

    @Override
    public ResponseEntity<GenericResponse<Void>> logoutUser() {
        var cookie = jwtUtilities.getCleanJwtCookie();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(GenericResponse.empty());
    }
}