package com.colegio.sistema.Security;

import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Service
@Primary
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByDni(dni)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + dni));

        // Aseguramos que el rol esté en formato: ROLE_ADMIN, ROLE_ALUMNO, etc.
        String rolFormateado = "ROLE_" + usuario.getRol().name().toUpperCase();

        return new User(
            usuario.getDni(),
            usuario.getContrasena(),
            Collections.singletonList(new SimpleGrantedAuthority(rolFormateado))
        );
    }
}