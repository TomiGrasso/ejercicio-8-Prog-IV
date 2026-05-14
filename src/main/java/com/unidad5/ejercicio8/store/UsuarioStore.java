package com.unidad5.ejercicio8.store;

import com.unidad5.ejercicio8.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioStore {

    private List<Usuario> usuarios = new ArrayList<>();

    private Long contadorId = 1L;

    public void guardarUsuario(Usuario usuario){
        usuario.setId(contadorId++);
        usuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorUsername(String username){
        for (Usuario u : usuarios){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public boolean existeUsername(String username){
        for (Usuario u : usuarios){
            if (u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean existeEmail(String email){
        for (Usuario u : usuarios){
            if (u.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public List<Usuario> obtenerTodos(){
        return usuarios;
    }
}
