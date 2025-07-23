package com.colegio.sistema.Controller;

import com.colegio.sistema.Entity.Nota;
import com.colegio.sistema.Repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas")
public class NotaRestController {

    @Autowired
    private NotaRepository notaRepository;

    // Obtener notas de un curso
    @GetMapping
    public List<Nota> obtenerNotasPorCurso(
        @RequestParam Long cursoId,
        @RequestParam Long gradoId,
        @RequestParam Long seccionId
    ) {
        return notaRepository.findByCursoIdAndGradoIdAndSeccionId(cursoId, gradoId, seccionId);
    }

    // Guardar o actualizar nota
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarNota(@RequestBody Nota nota) {
        Optional<Nota> notaExistente = notaRepository.findByDniAlumnoAndCursoIdAndGradoIdAndSeccionId(
            nota.getDniAlumno(), nota.getCursoId(), nota.getGradoId(), nota.getSeccionId()
        );

        if (notaExistente.isPresent()) {
            Nota existente = notaExistente.get();
            existente.setPractica1(nota.getPractica1());
            existente.setPractica2(nota.getPractica2());
            existente.setPractica3(nota.getPractica3());
            existente.setPromedio(nota.getPromedio());
            notaRepository.save(existente);
        } else {
            notaRepository.save(nota);
        }

        return ResponseEntity.ok("Nota guardada correctamente");
    }
}