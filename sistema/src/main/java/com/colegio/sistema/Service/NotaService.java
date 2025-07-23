package com.colegio.sistema.Service;

import com.colegio.sistema.dto.AlumnoNotaCurso;
import com.colegio.sistema.Entity.Nota;

import java.util.List;

public interface NotaService {

    List<AlumnoNotaCurso> obtenerNotasPorCurso(Long cursoId, Long gradoId, Long seccionId);

    void guardarNota(AlumnoNotaCurso alumnoNotaCurso, Long cursoId, Long gradoId, Long seccionId);
}