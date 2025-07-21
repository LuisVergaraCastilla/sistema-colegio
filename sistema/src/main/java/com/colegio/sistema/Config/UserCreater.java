package com.colegio.sistema.Config;

import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Entity.Rol;
import com.colegio.sistema.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserCreater implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // DNI único para evitar duplicados si ya existe en la BD
        String dni = "12345678";

        // Solo creamos si no existe ya ese usuario
        boolean yaExiste = usuarioService.listarUsuarios().stream()
                .anyMatch(u -> u.getDni().equals(dni));

        if (!yaExiste) {
            Usuario user = new Usuario();
            user.setDni(dni);
            user.setNombre("Administrador");
            user.setContrasena(passwordEncoder.encode("admin123")); // contraseña cifrada
            user.setRol(Rol.ADMIN); // Usa tu enum correctamente
            user.setActivo(true);

            usuarioService.guardarUsuario(user);
            System.out.println("✅ Usuario ADMIN creado correctamente");
        } else {
            System.out.println("ℹ️ El usuario con DNI " + dni + " ya existe.");
        }
    }
}