package com.hrManagement.controller;

import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.logica.PerfilEmpleadoLogica;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfilEmpleado")
public class PerfilEmpleadoController {

    private final PerfilEmpleadoLogica perfilEmpleadoLogica;
    private final PerfilEmpleadoRepository perfilEmpleadoRepository;
    private final EmpleadoRepository empleadoRepository;

    public PerfilEmpleadoController(PerfilEmpleadoLogica perfilEmpleadoLogica, PerfilEmpleadoRepository perfilEmpleadoRepository, EmpleadoRepository empleadoRepository) {
        this.perfilEmpleadoLogica = perfilEmpleadoLogica;
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarPerfilEmpleado(@RequestBody PerfilEmpleadoDTO perfilEmpleadoDTO) {
        try {
            boolean resultado = perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);
            if (resultado) {
                return ResponseEntity.ok("Perfil de empleado agregado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el perfil de empleado.");
            }
        } catch (PerfilEmpleadoLogica.EmpleadoNoExisteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear el perfil ya que no hay un empleado con ese código");
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<PerfilEmpleado> obtenerDatosEmpleadoPorID(@PathVariable int id) {
        PerfilEmpleado perfilEmpleado = perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(id);
        if (perfilEmpleado != null) {
            return ResponseEntity.ok(perfilEmpleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todos")
    public List<PerfilEmpleado> obtenerTodosLosPerfilesDeEmpleados() {
        return perfilEmpleadoLogica.obtenerTodosLosPerfilesDeEmpleados();
    }
    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<String> eliminarPerfilEmpleado(@PathVariable int codigo) {
        try {
            boolean resultado = perfilEmpleadoLogica.eliminarPerfilEmpleado(codigo);
            if (resultado) {
                return ResponseEntity.ok("Perfil del empleado desactivado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo desactivar el perfil del empleado.");
            }
        } catch (PerfilEmpleadoLogica.EmpleadoNoExisteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede desactivar el perfil del empleado ya que no existe.");
        }
    }
}
