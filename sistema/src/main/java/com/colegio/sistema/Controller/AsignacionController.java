package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Asignacion;
import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Service.AsignacionService;
import com.colegio.sistema.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Asignacion crearAsignacion(@RequestBody Asignacion asignacion) {
        return asignacionService.guardarAsignacion(asignacion);
    }

    @GetMapping("/profesor/{id}")
    public List<Asignacion> obtenerAsignacionesProfesor(@PathVariable Long id) {
        Usuario profesor = usuarioService.obtenerPorId(id);
        return asignacionService.obtenerPorProfesor(profesor);
    }
}
