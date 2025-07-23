package com.colegio.sistema.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoNotaCurso {
    private Long id;
    private String dni;
    private String nombre;

    private Double practica1;
    private Double practica2;
    private Double practica3;
    private Double promedio;
}