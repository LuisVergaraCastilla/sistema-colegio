package com.colegio.sistema.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ControllerViewLogin {

    @GetMapping("/")
    public String mostrarLogin() {
        return "login"; // Vista login.html
    }

    @GetMapping("/redirigir-por-rol")
    public void redirigirPorRol(Authentication auth, HttpServletResponse response) throws IOException {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/usuarios");
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESOR"))) {
            response.sendRedirect("/profesor/inicio");
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ALUMNO"))) {
            response.sendRedirect("/alumno/inicio");
        } else {
            response.sendRedirect("/login?error");
        }
    }
}