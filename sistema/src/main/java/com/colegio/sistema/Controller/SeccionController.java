package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Seccion;
import com.colegio.sistema.Repository.SeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
public class SeccionController {

    @Autowired
    private SeccionRepository seccionRepository;

    @GetMapping
    public List<Seccion> obtenerSecciones() {
        return seccionRepository.findAll();
    }
}
