package com.unidad5.ejercicio8.security;

import com.unidad5.ejercicio8.model.Usuario;
import com.unidad5.ejercicio8.store.UsuarioStore;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioStore usuarioStore;

    private CustomUserDetailsService(UsuarioStore usuarioStore) {
        this.usuarioStore = usuarioStore;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioStore.buscarUsuarioPorUsername(username);

        if (usuario == null){
            throw new  UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }
}
/*
 * GUIA DE RESOLUCION
 *
 * Esta clase normalmente implementa UserDetailsService.
 *
 * Objetivo:
 * - Buscar un usuario por username.
 * - Convertirlo a UserDetails con sus authorities.
 *
 * Como el ejercicio pide trabajar en memoria, se puede conectar con un servicio
 * que mantenga usuarios en un Map<String, Usuario>.
 */
