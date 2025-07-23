package com.colegio.sistema.Entity;

import jakarta.persistence.*;

@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;
    private String nombre;
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "asignacion_id")
    private Asignacion asignacion;

    // Constructor vacío obligatorio
    public Alumno() {}

    public Alumno(String dni, String nombre, Double nota) {
        this.dni = dni;
        this.nombre = nombre;
        this.nota = nota;
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public Double getNota() { return nota; }
    public Asignacion getAsignacion() { return asignacion; }

    public void setId(Long id) { this.id = id; }
    public void setDni(String dni) { this.dni = dni; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNota(Double nota) { this.nota = nota; }
    public void setAsignacion(Asignacion asignacion) { this.asignacion = asignacion; }
}
