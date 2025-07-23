package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> listarAlumnos() {
        return alumnoService.listarTodos();
    }

    @PostMapping
    public Alumno guardarAlumno(@RequestBody Alumno alumno) {
        return alumnoService.guardar(alumno);
    }

    @GetMapping("/{id}")
    public Alumno obtenerAlumno(@PathVariable Long id) {
        return alumnoService.obtenerPorId(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminarAlumno(@PathVariable Long id) {
        alumnoService.eliminarPorId(id);
    }
}