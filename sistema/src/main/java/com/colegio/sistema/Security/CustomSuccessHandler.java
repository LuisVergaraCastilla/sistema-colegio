package com.colegio.sistema.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String rol = auth.getAuthority();

            if (rol.equals("ROLE_ALUMNO")) {
                response.sendRedirect("/alumno/inicio");
                return;
            } else if (rol.equals("ROLE_PROFESOR")) {
                response.sendRedirect("/profesor/inicio");
                return;
            } else if (rol.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/inicio");
                return;
            }
        }

        // En caso no tenga ningún rol válido
        response.sendRedirect("/login?error");
    }
}