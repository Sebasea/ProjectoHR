package com.hrManagement.modelo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PerfilEmpleado {

@Id
@Column
private int codigo;
@Column
private String nombre;
@Column
private String habilidades;
@Column
private String experiencia;
@Column
private String certificaciones;
@Column
boolean eliminar;

  public PerfilEmpleado(int codigo, String nombre, String habilidades, String experiencia, String certificaciones, boolean eliminar) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.habilidades = habilidades;
    this.experiencia = experiencia;
    this.certificaciones = certificaciones;
    this.eliminar = eliminar;
  }

  public PerfilEmpleado() {

  }
}
