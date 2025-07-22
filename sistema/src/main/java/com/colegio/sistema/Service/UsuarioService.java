package com.colegio.sistema.Service;

import com.colegio.sistema.Entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listarUsuarios();

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    Optional<Usuario> obtenerUsuarioPorDni(String dni);

    Optional<Usuario> buscarUsuarioPorId(Long id); // ← nuevo método
}