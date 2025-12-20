package com.hasanoztunc.library_automation.features.role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoleDTO> createRole(RoleDTO roleDTO) {
        var role = roleService.createRole(roleDTO);

        return ResponseEntity.ok(role);
    }
}