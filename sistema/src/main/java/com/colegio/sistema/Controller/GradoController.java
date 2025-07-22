package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Grado;
import com.colegio.sistema.Repository.GradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
public class GradoController {

    @Autowired
    private GradoRepository gradoRepository;

    @GetMapping
    public List<Grado> obtenerGrados() {
        return gradoRepository.findAll();
    }
}
