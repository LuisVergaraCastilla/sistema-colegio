package com.colegio.sistema.Service;

import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Entity.Asignacion;
import com.colegio.sistema.Entity.Usuario;
import com.colegio.sistema.Repository.AlumnoRepository;
import com.colegio.sistema.Repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Override
    public Asignacion guardarAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    @Override
    public List<Asignacion> obtenerPorProfesor(Usuario profesor) {
        return asignacionRepository.findByProfesor(profesor);
    }

    @Autowired
private AlumnoRepository alumnoRepository;

@Override
public List<Alumno> obtenerAlumnosPorAsignacion(Asignacion asignacion) {
    return alumnoRepository.findByAsignacion(asignacion);
}
}