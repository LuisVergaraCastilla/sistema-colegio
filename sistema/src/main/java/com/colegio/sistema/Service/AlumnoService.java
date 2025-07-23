package com.colegio.sistema.Service;

import com.colegio.sistema.Entity.Alumno;
import com.colegio.sistema.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> listarTodos() {
        return alumnoRepository.findAll();
    }

    public Alumno guardar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public Optional<Alumno> obtenerPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        alumnoRepository.deleteById(id);
    }
}
