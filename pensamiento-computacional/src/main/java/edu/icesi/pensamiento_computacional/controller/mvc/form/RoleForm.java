package edu.icesi.pensamiento_computacional.controller.mvc.form;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleForm {

    @NotBlank(message = "El nombre del rol es obligatorio.")
    private String name;

    @NotBlank(message = "La descripción es obligatoria.")
    private String description;

    @NotEmpty(message = "Selecciona al menos un permiso.")
    private Set<Integer> permissionIds = new HashSet<>();
}
