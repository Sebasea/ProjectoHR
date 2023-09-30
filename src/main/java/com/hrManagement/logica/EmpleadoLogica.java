package com.hrManagement.logica;

import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmpleadoLogica {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoLogica(EmpleadoRepository empleadoRepository) {

        this.empleadoRepository = empleadoRepository;
    }

    public boolean guardarEmpleado(EmpleadoDTO empleadoDTO) {
        if (empleadoRepository.findById(empleadoDTO.getCodigo()).isPresent()) {
            return false;
        }

        Empleado empleado = new Empleado();
        empleado.setCodigo(empleadoDTO.getCodigo());
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setEdad(empleadoDTO.getEdad());
        empleado.setRol(RolEnum.valueOf(empleadoDTO.getRol().name()));
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setNumeroTelefonico(empleadoDTO.getNumeroTelefonico());
        empleado.setResponsabilidades(empleadoDTO.getResponsabilidades());
        empleado.setEliminar(false);

        try {
            empleadoRepository.save(empleado);
            return true;
        } catch (Exception e) {
            // log the exception instead of printing the stack trace
            Logger logger = LoggerFactory.getLogger(EmpleadoLogica.class);
            logger.error("Error while saving Empleado object", e);
            return false;
        }
    }
    public Empleado obtenerEmpleadoPorID(int id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public List<Empleado> obtenerEmpleadosPorCargo(Enum rol) {
        return empleadoRepository.findByRol(rol);
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public boolean modificarEmpleado(int codigo, EmpleadoDTO empleadoActualizado) {
        if (empleadoRepository.existsById(codigo)) {
            Empleado empleado = empleadoRepository.findById(codigo).orElse(null);
            if (empleado != null) {
                // Actualiza los campos del empleado con los del DTO
                empleado.setNombre(empleadoActualizado.getNombre());
                empleado.setEdad(empleadoActualizado.getEdad());
                empleado.setRol(RolEnum.valueOf(empleadoActualizado.getRol().name()));
                empleado.setEmail(empleadoActualizado.getEmail());
                empleado.setNumeroTelefonico(empleadoActualizado.getNumeroTelefonico());
                empleado.setResponsabilidades(empleadoActualizado.getResponsabilidades());
                empleadoRepository.save(empleado);
                return true;
            }
        }
        return false;
    }
    public boolean eliminarEmpleado(int codigo) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(codigo);
        Empleado empleado = new Empleado();
        empleado.setEliminar(false);

        if (empleadoOptional.isPresent()) {
            empleado = empleadoOptional.get();
            empleado.setEliminar(true);

            try {
                empleadoRepository.save(empleado);
                return true;
            } catch (Exception e) {
                Logger logger = LoggerFactory.getLogger(EmpleadoLogica.class);
                logger.error("Error while saving Empleado object", e);
                return false;
            }
        }

        return false;
    }

    public static class EmpleadoNoExisteException extends RuntimeException {
        public EmpleadoNoExisteException() {
            super("No existe Empleado");
        }
    }
}
