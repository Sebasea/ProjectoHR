package com.hrManagement.controller;

import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.logica.PerfilEmpleadoLogica;
import com.hrManagement.logica.RolEnum;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.logica.EmpleadoLogica;
import com.hrManagement.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoLogica empleadoLogica;

    private final EmpleadoRepository empleadoRepository;
    public EmpleadoController(EmpleadoLogica empleadoLogica, EmpleadoRepository empleadoRepository) {
        this.empleadoLogica = empleadoLogica;
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        boolean guardado = empleadoLogica.guardarEmpleado(empleadoDTO);
        if (guardado) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Empleado agregado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el empleado");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerDatosEmpleadoPorID(@PathVariable int codigo) {
        Empleado empleado = empleadoLogica.obtenerEmpleadoPorID(codigo);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Empleado> obtenerEmpleadosPorCargo(@RequestParam Enum rol) {
        return empleadoLogica.obtenerEmpleadosPorCargo(RolEnum.valueOf(String.valueOf(rol)));
    }

    @GetMapping("/todos")
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoLogica.obtenerTodosLosEmpleados();
    }
    @PutMapping("/modificar/{codigo}")
    public ResponseEntity<String> modificarEmpleado(@PathVariable int codigo, @RequestBody EmpleadoDTO empleadoActualizado) {
        boolean modificado = empleadoLogica.modificarEmpleado(codigo, empleadoActualizado);
        if (modificado) {
            return ResponseEntity.ok("Empleado actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }
    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable int codigo) {
        try {
            boolean resultado = empleadoLogica.eliminarEmpleado(codigo);
            if (resultado) {
                return ResponseEntity.ok("Empleado desactivado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo desactivar al empleado.");
            }
        } catch (PerfilEmpleadoLogica.EmpleadoNoExisteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede desactivar el empleado ya que no existe.");
        }
    }
}
