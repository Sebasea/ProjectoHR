package com.hrManagement.modelo;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Empleado {
    @Id
    @Column
    int codigo;
    @Column
    String nombre;
    @Column
    int edad;
    @Column
    Enum rol;
    @Column
    String email;
    @Column
    int numeroTelefonico;
    @Column
    String responsabilidades;
    @Column
    boolean eliminar;

    public Empleado(int codigo, String nombre, int edad, Enum rol, String email, int numeroTelefonico, String responsabilidades, boolean eliminar) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
        this.rol = rol;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
        this.responsabilidades = responsabilidades;
        this.eliminar = eliminar;
    }

    public Empleado() {

    }
}
