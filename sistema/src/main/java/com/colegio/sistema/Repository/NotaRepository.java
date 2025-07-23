package com.colegio.sistema.Repository;

import com.colegio.sistema.Entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByCursoIdAndGradoIdAndSeccionId(Long cursoId, Long gradoId, Long seccionId);

    Optional<Nota> findByDniAlumnoAndCursoIdAndGradoIdAndSeccionId(
        String dniAlumno, Long cursoId, Long gradoId, Long seccionId
    );
}