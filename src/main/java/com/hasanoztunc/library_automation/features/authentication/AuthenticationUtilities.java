package com.hasanoztunc.library_automation.features.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationUtilities {

    private final MemberRepository memberRepository;

    public AuthenticationUtilities(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> getLoggedInMember() {
        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return memberRepository.findByUsername(authentication.getName());
    }
}