package edu.icesi.pensamiento_computacional.controller.mvc.form;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationForm {

    @NotBlank(message = "El nombre es obligatorio.")
    private String fullName;

    @NotBlank(message = "El correo institucional es obligatorio.")
    @Email(message = "Ingresa un correo institucional válido.")
    private String institutionalEmail;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres.")
    private String password;

    @NotEmpty(message = "Selecciona al menos un rol.")
    private List<Integer> roleIds = new ArrayList<>();
}
