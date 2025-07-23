package com.colegio.sistema.Repository;

import com.colegio.sistema.Entity.Asignacion;
import com.colegio.sistema.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    List<Asignacion> findByProfesor(Usuario profesor);
}