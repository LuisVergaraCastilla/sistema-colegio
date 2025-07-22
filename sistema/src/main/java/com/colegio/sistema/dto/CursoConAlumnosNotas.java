package com.colegio.sistema.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoConAlumnosNotas {
    private String nombre;
    private String grado;
    private String seccion;
    private List<AlumnoNotaCurso> alumnos;
}
