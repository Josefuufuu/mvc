package edu.icesi.pensamiento_computacional.controller.mvc.form;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionAssignmentForm {

    @NotNull(message = "Selecciona un rol válido.")
    private Integer roleId;

    @NotEmpty(message = "Selecciona al menos un permiso.")
    private Set<Integer> permissionIds;
}
