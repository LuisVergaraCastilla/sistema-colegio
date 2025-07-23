package com.colegio.sistema.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dniAlumno;

    private Long cursoId;
    private Long gradoId;
    private Long seccionId;

    private Double practica1;
    private Double practica2;
    private Double practica3;
    private Double promedio;
}