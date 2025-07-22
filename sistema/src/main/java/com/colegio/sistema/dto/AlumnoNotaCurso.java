package com.colegio.sistema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoNotaCurso {
    private Long id;
    private String dni;
    private String nombre;
    private Double nota;
}
