package com.unidad5.ejercicio8.controller;

import com.unidad5.ejercicio8.dto.AuthResponseDTO;
import com.unidad5.ejercicio8.dto.LoginRequestDTO;
import com.unidad5.ejercicio8.dto.RegisterRequestDTO;
import com.unidad5.ejercicio8.model.Usuario;
import com.unidad5.ejercicio8.security.JwtAuthenticationFilter;
import com.unidad5.ejercicio8.security.JwtService;
import com.unidad5.ejercicio8.service.UsuarioService;
import com.unidad5.ejercicio8.store.UsuarioStore;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioStore usuarioStore;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController (UsuarioService usuarioService, UsuarioStore usuarioStore, AuthenticationManager authenticationManager, JwtService jwtService){
        this.usuarioService = usuarioService;
        this.usuarioStore = usuarioStore;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegisterRequestDTO dto){
        if (usuarioStore.existeUsername(dto.getUsername())){
            return ResponseEntity.status(409).body("El username ya está en uso");
        }
        if (usuarioStore.existeEmail(dto.getEmail())){
            return ResponseEntity.status(409).body("El mail ya está en uso");
        }

        usuarioService.crear(dto);
        return ResponseEntity.status(201).body("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> logear(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        Usuario usuario = (Usuario) auth.getPrincipal();

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : usuario.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        String token = jwtService.generarToken(usuario.getUsername(), roles);

        AuthResponseDTO response = new AuthResponseDTO(token, 3600, usuario.getUsername());
        return ResponseEntity.ok(response);
    }
}
