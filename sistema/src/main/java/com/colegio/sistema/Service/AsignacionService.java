package com.colegio.sistema.Service;

import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Entity.Asignacion;
import com.colegio.sistema.Entity.Usuario;

import java.util.List;

public interface AsignacionService {
    Asignacion guardarAsignacion(Asignacion asignacion);
    List<Asignacion> obtenerPorProfesor(Usuario profesor);
    List<Alumno> obtenerAlumnosPorAsignacion(Asignacion asignacion);
}
