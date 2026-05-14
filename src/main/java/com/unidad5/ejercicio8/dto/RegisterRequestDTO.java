package com.unidad5.ejercicio8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Campo obligatorio")
    private String username;

    @NotBlank(message = "Campo obligatorio")
    @Email(message = "Formato no válido")
    private String email;

    @NotBlank(message = "Campo obligatorio")
    @Size(min = 8, message = "Contraseña muy corta. Mínimo 8 caracteres")
    private String password;
}

