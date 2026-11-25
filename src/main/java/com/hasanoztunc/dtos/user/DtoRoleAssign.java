package com.hasanoztunc.dtos.user;

import com.hasanoztunc.enumerations.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoRoleAssign {

    @NotNull
    private Role role;
}
