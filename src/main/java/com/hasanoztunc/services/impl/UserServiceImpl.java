package com.hasanoztunc.services.impl;

import com.hasanoztunc.enumerations.MessageType;
import com.hasanoztunc.enumerations.Role;
import com.hasanoztunc.exceptions.BaseException;
import com.hasanoztunc.exceptions.ErrorMessage;
import com.hasanoztunc.repositories.UserRepository;
import com.hasanoztunc.security.entities.User;
import com.hasanoztunc.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Set<Role> assignRole(Long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new BaseException(
                        new ErrorMessage(
                                MessageType.NO_RECORD_EXISTS, id.toString()
                        )
                )
        );

        user.getRoles().add(role);

        userRepository.saveAndFlush(user);

        return user.getRoles();
    }
}
