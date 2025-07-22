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
public class PanelProfesorData {
    private String nombre;
    private String dni;
    private String grado; // Grado que enseña el profesor
    private String seccion; // Sección que enseña el profesor
    private List<CursoVistaProfesor> cursos; // Lista de cursos que dicta el profesor
}
