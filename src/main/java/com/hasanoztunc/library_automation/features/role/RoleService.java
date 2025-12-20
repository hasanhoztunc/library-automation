package com.hasanoztunc.library_automation.features.role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    Optional<RoleDTO> getRoleById(Integer roleId);

    List<RoleDTO> getRolesByName(String roleName);

    RoleDTO updateRole(Integer roleId, RoleDTO roleDTO);

    void deleteRole(Integer roleId);
}