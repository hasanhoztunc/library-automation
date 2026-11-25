package com.hasanoztunc.controllers;

import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.enumerations.Role;

import java.util.Set;

public interface UserController {

    RootEntity<Set<Role>> assignRole(Long id, Role role);
}
