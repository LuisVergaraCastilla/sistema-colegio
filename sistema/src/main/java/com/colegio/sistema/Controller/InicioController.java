package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class InicioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/alumno/inicio")
    public String inicioAlumno(Model model, Principal principal) {
        Usuario usuario = usuarioService.obtenerUsuarioPorDni(principal.getName()).orElse(null);
        model.addAttribute("usuario", usuario);
        return "alumno/inicio";
    }

    @GetMapping("/profesor/inicio")
    public String inicioProfesor(Model model, Principal principal) {
        Usuario usuario = usuarioService.obtenerUsuarioPorDni(principal.getName()).orElse(null);
        model.addAttribute("usuario", usuario);
        return "profesor/inicio";
    }
}
