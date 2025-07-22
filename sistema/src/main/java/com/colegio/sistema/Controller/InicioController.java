package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Entity.Rol; // Necesitas importar Rol si lo usas en Usuario
import com.colegio.sistema.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

// --- Importaciones de DTOs ---
import com.colegio.sistema.dto.CursoVistaProfesor;
import com.colegio.sistema.dto.AlumnoNotaCurso;
import com.colegio.sistema.dto.CursoConAlumnosNotas;
import com.colegio.sistema.dto.PanelProfesorData;
// --- Fin de Importaciones de DTOs ---


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
        System.out.println("DEBUG: Entrando a InicioController.inicioProfesor() - Ruta: /profesor/inicio");

        // Obtener el usuario autenticado (el profesor)
        Usuario usuarioActual = usuarioService.obtenerUsuarioPorDni(principal.getName()).orElse(null);
        
        // Si el usuario no se encuentra, redirigir a una página de error o login
        if (usuarioActual == null) {
            System.err.println("ERROR: Usuario autenticado no encontrado en la base de datos para DNI: " + principal.getName());
            return "redirect:/login?error"; // O una página de error más específica
        }

        model.addAttribute("usuarioActual", usuarioActual);
        System.out.println("DEBUG: usuarioActual añadido al modelo. Nombre: " + usuarioActual.getNombre());

        // --- DATOS DE PRUEBA ESTRUCTURADOS ---
        // Estos datos simulan lo que el admin asignaría.
        // En un futuro, esta lógica se reemplazaría con llamadas a la base de datos
        // para obtener los cursos y alumnos reales asignados al profesor.

        // Cursos que dicta el profesor (incluyendo grado y sección)
        List<CursoVistaProfesor> cursosDictados = new ArrayList<>();
        cursosDictados.add(new CursoVistaProfesor("Matemáticas", "5to", "A"));
        cursosDictados.add(new CursoVistaProfesor("Física", "4to", "B"));
        cursosDictados.add(new CursoVistaProfesor("Medioambiente", "3ro", "C"));

        // Datos del panel del profesor
        PanelProfesorData profesorData = new PanelProfesorData(
            usuarioActual.getNombre(),
            usuarioActual.getDni(),
            "5to", // Grado que enseña (ejemplo) - Esto debería venir de la DB para el profesor
            "A",   // Sección (ejemplo) - Esto debería venir de la DB para el profesor
            cursosDictados
        );
        model.addAttribute("profesorData", profesorData);

        // Datos específicos del curso "Medioambiente" con sus alumnos y notas
        List<AlumnoNotaCurso> alumnosMedioambiente = new ArrayList<>();
        alumnosMedioambiente.add(new AlumnoNotaCurso(101L, "98765432A", "Juan Pérez", 15.5));
        alumnosMedioambiente.add(new AlumnoNotaCurso(102L, "11223344B", "María García", 18.0));
        alumnosMedioambiente.add(new AlumnoNotaCurso(103L, "55667788C", "Carlos López", 12.0));
        alumnosMedioambiente.add(new AlumnoNotaCurso(104L, "99001122D", "Ana Martínez", 19.5));

        CursoConAlumnosNotas cursoMedioambiente = new CursoConAlumnosNotas(
            "Medioambiente",
            "3ro",
            "C",
            alumnosMedioambiente
        );
        model.addAttribute("cursoMedioambiente", cursoMedioambiente);

        return "profesor/inicio";
    }
}
