package edu.icesi.pensamiento_computacional.controller.mvc.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    @NotBlank(message = "El correo institucional es obligatorio.")
    @Email(message = "Ingresa un correo institucional válido.")
    private String institutionalEmail;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;
}
