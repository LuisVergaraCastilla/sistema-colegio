package com.colegio.sistema.Service.impl;

import com.colegio.sistema.dto.AlumnoNotaCurso;
import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Entity.Nota;
import com.colegio.sistema.Repository.AlumnoRepository;
import com.colegio.sistema.Repository.NotaRepository;
import com.colegio.sistema.Service.NotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public List<AlumnoNotaCurso> obtenerNotasPorCurso(Long cursoId, Long gradoId, Long seccionId) {
        List<Alumno> alumnos = alumnoRepository
    .findByAsignacion_Grado_IdAndAsignacion_Seccion_IdAndAsignacion_Curso_Id(gradoId, seccionId, cursoId);
        List<Nota> notas = notaRepository.findByCursoIdAndGradoIdAndSeccionId(cursoId, gradoId, seccionId);

        Map<String, Nota> notaMap = new HashMap<>();
        for (Nota nota : notas) {
            notaMap.put(nota.getDniAlumno(), nota);
        }

        List<AlumnoNotaCurso> resultado = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            Nota nota = notaMap.get(alumno.getDni());
            AlumnoNotaCurso dto = new AlumnoNotaCurso();
            dto.setId(alumno.getId());
            dto.setDni(alumno.getDni());
            dto.setNombre(alumno.getNombre());

            if (nota != null) {
                dto.setPractica1(nota.getPractica1());
                dto.setPractica2(nota.getPractica2());
                dto.setPractica3(nota.getPractica3());
                dto.setPromedio(nota.getPromedio());
            } else {
                dto.setPractica1(null);
                dto.setPractica2(null);
                dto.setPractica3(null);
                dto.setPromedio(null);
            }

            resultado.add(dto);
        }

        return resultado;
    }

    @Override
    public void guardarNota(AlumnoNotaCurso alumnoNotaCurso, Long cursoId, Long gradoId, Long seccionId) {
        Nota nota = notaRepository.findByDniAlumnoAndCursoIdAndGradoIdAndSeccionId(
                alumnoNotaCurso.getDni(), cursoId, gradoId, seccionId
        ).orElse(new Nota());

        nota.setDniAlumno(alumnoNotaCurso.getDni());
        nota.setCursoId(cursoId);
        nota.setGradoId(gradoId);
        nota.setSeccionId(seccionId);

        nota.setPractica1(alumnoNotaCurso.getPractica1());
        nota.setPractica2(alumnoNotaCurso.getPractica2());
        nota.setPractica3(alumnoNotaCurso.getPractica3());
        nota.setPromedio(alumnoNotaCurso.getPromedio());

        notaRepository.save(nota);
    }
}