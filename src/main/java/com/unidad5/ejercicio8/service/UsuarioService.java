package com.unidad5.ejercicio8.service;

import com.unidad5.ejercicio8.dto.RegisterRequestDTO;

import com.unidad5.ejercicio8.model.Usuario;
import com.unidad5.ejercicio8.store.UsuarioStore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioStore usuarioStore;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioStore usuarioStore, PasswordEncoder passwordEncoder){
        this.usuarioStore = usuarioStore;
        this.passwordEncoder = passwordEncoder;
    }

    public void crear(RegisterRequestDTO dto) {
        Usuario usuario = toUsuarioFromRegisterDTO(dto);
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_PACIENTE");
        usuario.setRoles(roles);

        usuarioStore.guardarUsuario(usuario);
    }

    private Usuario toUsuarioFromRegisterDTO(RegisterRequestDTO registerDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(registerDTO.getUsername());
        usuario.setEmail(registerDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return usuario;
    }
}
