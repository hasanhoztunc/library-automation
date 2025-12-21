package com.hasanoztunc.library_automation.features.role;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
final public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(
            RoleRepository roleRepository,
            ModelMapper modelMapper
    ) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        var role = modelMapper.map(roleDTO, Role.class);
        var savedRole = roleRepository.save(role);

        return modelMapper.map(savedRole, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        var roles = roleRepository.findAll();

        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .toList();
    }

    @Override
    public Optional<RoleDTO> getRoleById(Integer roleId) {
        return Optional.empty();
    }

    @Override
    public List<RoleDTO> getRolesByName(String roleName) {
        return List.of();
    }

    @Override
    public RoleDTO updateRole(Integer roleId, RoleDTO roleDTO) {
        return null;
    }

    @Override
    public void deleteRole(Integer roleId) {

    }
}