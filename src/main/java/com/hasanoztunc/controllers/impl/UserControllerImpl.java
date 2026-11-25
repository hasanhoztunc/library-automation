package com.hasanoztunc.controllers.impl;

import com.hasanoztunc.controllers.UserController;
import com.hasanoztunc.controllers.impl.base.BaseController;
import com.hasanoztunc.dtos.base.RootEntity;
import com.hasanoztunc.enumerations.Role;
import com.hasanoztunc.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/rest/api/user")
public class UserControllerImpl extends BaseController implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}/update-role")
    @Override
    public RootEntity<Set<Role>> assignRole(@PathVariable(name = "id", required = true) Long id, @RequestBody Role role) {
        return ok(
                userService.assignRole(id, role)
        );
    }
}
