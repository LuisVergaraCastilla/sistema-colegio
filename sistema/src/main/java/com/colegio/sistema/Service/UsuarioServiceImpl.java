package com.colegio.sistema.Service;

import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Este método es requerido por Spring Security
    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con DNI: " + dni));

        // Convertimos el rol a una autoridad para Spring Security
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));

        return new org.springframework.security.core.userdetails.User(
                usuario.getDni(),
                usuario.getContrasena(),
                usuario.isActivo(),  // Habilitado
                true,                // cuenta no expirada
                true,                // credenciales no expiradas
                true,                // cuenta no bloqueada
                authorities
        );
    }
}