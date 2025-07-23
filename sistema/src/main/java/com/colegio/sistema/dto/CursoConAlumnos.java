package com.colegio.sistema.dto;

import com.colegio.sistema.Entity.Alumno;
import java.util.List;

public class CursoConAlumnos {
    private String nombre;
    private String grado;
    private String seccion;
    private List<Alumno> alumnos;

    public CursoConAlumnos(String nombre, String grado, String seccion, List<Alumno> alumnos) {
        this.nombre = nombre;
        this.grado = grado;
        this.seccion = seccion;
        this.alumnos = alumnos;
    }

    public String getNombre() { return nombre; }
    public String getGrado() { return grado; }
    public String getSeccion() { return seccion; }
    public List<Alumno> getAlumnos() { return alumnos; }
}