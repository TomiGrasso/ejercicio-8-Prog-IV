package com.unidad5.ejercicio8.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Campo obligatorio")
    private String username;

    @NotBlank(message = "Campo obligatorio")
    private String password;
}