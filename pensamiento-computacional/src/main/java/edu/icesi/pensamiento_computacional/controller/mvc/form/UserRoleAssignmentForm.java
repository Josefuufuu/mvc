package edu.icesi.pensamiento_computacional.controller.mvc.form;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleAssignmentForm {

    @NotNull(message = "Selecciona un usuario válido.")
    private Integer userId;

    @NotEmpty(message = "Selecciona al menos un rol.")
    private Set<Integer> roleIds;
}
