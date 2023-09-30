package com.hrManagement.controller.dto;
import com.hrManagement.logica.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private int codigo;
    private String nombre;
    private int edad;
    private RolEnum rol;
    private String email;
    private int numeroTelefonico;
    private String responsabilidades;
}