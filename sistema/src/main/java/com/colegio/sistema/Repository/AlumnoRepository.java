package com.colegio.sistema.Repository;

import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Entity.Asignacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Puedes agregar métodos personalizados si lo necesitas más adelante
    List<Alumno> findByAsignacion(Asignacion asignacion);
    List<Alumno> findByAsignacion_Grado_IdAndAsignacion_Seccion_IdAndAsignacion_Curso_Id(
    Long gradoId, Long seccionId, Long cursoId
);
}