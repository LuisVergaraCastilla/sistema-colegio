package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Entity.Asignacion;
import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Service.UsuarioService;
import com.colegio.sistema.Service.AsignacionService;
import com.colegio.sistema.dto.CursoVistaProfesor;
import com.colegio.sistema.dto.PanelProfesorData;
import com.colegio.sistema.dto.CursoConAlumnos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InicioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AsignacionService asignacionService;

    @GetMapping("/profesor/inicio")
    public String inicioProfesor(Model model, Principal principal) {
        Usuario profesor = usuarioService.obtenerUsuarioPorDni(principal.getName()).orElse(null);

        if (profesor == null) {
            return "redirect:/login?error";
        }

        model.addAttribute("usuarioActual", profesor);

        List<Asignacion> asignaciones = asignacionService.obtenerPorProfesor(profesor);

        List<CursoVistaProfesor> cursosDictados = new ArrayList<>();
        for (Asignacion asignacion : asignaciones) {
            cursosDictados.add(new CursoVistaProfesor(
                asignacion.getCurso().getNombre(),
                asignacion.getGrado().getNombre(),
                asignacion.getSeccion().getNombre()
            ));
        }

        String grado = "-";
        String seccion = "-";

        if (!asignaciones.isEmpty()) {
            grado = asignaciones.get(0).getGrado().getNombre();
            seccion = asignaciones.get(0).getSeccion().getNombre();
        }

        PanelProfesorData profesorData = new PanelProfesorData(
            profesor.getNombre(),
            profesor.getDni(),
            grado,
            seccion,
            cursosDictados
        );

        model.addAttribute("profesorData", profesorData);

        // 👉 Cargar curso ejemplo (solo si existe)
        // 👉 Cargar todos los cursos con sus alumnos
List<CursoConAlumnos> cursosConAlumnos = new ArrayList<>();

for (Asignacion asignacion : asignaciones) {
    List<Alumno> alumnos = asignacionService.obtenerAlumnosPorAsignacion(asignacion);

    CursoConAlumnos cursoConAlumnos = new CursoConAlumnos(
        asignacion.getCurso().getNombre(),
        asignacion.getGrado().getNombre(),
        asignacion.getSeccion().getNombre(),
        alumnos
    );

    cursosConAlumnos.add(cursoConAlumnos);
}

model.addAttribute("cursosConAlumnos", cursosConAlumnos);
        return "profesor/inicio";
    }

    @GetMapping("/alumno/inicio")
    public String inicioAlumno(Model model, Principal principal) {
        Usuario usuario = usuarioService.obtenerUsuarioPorDni(principal.getName()).orElse(null);
        model.addAttribute("usuario", usuario);
        return "alumno/inicio";
    }
}