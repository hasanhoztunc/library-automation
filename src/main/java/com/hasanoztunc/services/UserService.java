package com.hasanoztunc.services;

import com.hasanoztunc.enumerations.Role;

import java.util.Set;

public interface UserService {

    Set<Role> assignRole(Long id, Role role);
}
