package com.hrManagement.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilEmpleadoDTO {
    private int codigo;
    private String nombre;
    private String habilidades;
    private String experiencia;
    private String certificaciones;
    private boolean eliminar;
}
