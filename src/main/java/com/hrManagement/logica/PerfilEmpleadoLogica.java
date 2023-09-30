package com.hrManagement.logica;

import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilEmpleadoLogica {

    private final PerfilEmpleadoRepository perfilEmpleadoRepository;
    private final EmpleadoRepository empleadoRepository;

    public PerfilEmpleadoLogica(PerfilEmpleadoRepository perfilEmpleadoRepository, EmpleadoRepository empleadoRepository) {
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
        this.empleadoRepository = empleadoRepository;
    }
    public class CamposVaciosException extends RuntimeException {

        public CamposVaciosException(String message) {
            super(message);
        }
    }

    public boolean guardarPerfilEmpleado(PerfilEmpleadoDTO perfilEmpleadoDTO) {
        if (perfilEmpleadoDTO.getNombre() == null || perfilEmpleadoDTO.getNombre().isEmpty()) {
            throw new CamposVaciosException("El nombre no puede estar vacío");
        }

        if (perfilEmpleadoDTO.getHabilidades() == null || perfilEmpleadoDTO.getHabilidades().isEmpty()) {
            throw new CamposVaciosException("Las habilidades no pueden estar vacías");
        }

        if (perfilEmpleadoDTO.getExperiencia() == null || perfilEmpleadoDTO.getExperiencia().isEmpty()) {
            throw new CamposVaciosException("La experiencia no puede estar vacía");
        }

        if (perfilEmpleadoDTO.getCertificaciones() == null || perfilEmpleadoDTO.getCertificaciones().isEmpty()) {
            throw new CamposVaciosException("Las certificaciones no pueden estar vacías");
        }

        Optional<Empleado> empleadoOptional = empleadoRepository.findById(perfilEmpleadoDTO.getCodigo());

        if (empleadoOptional.isEmpty()) {
            throw new EmpleadoNoExisteException();
        }

        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setNombre(perfilEmpleadoDTO.getNombre());
        perfilEmpleado.setHabilidades(perfilEmpleadoDTO.getHabilidades());
        perfilEmpleado.setExperiencia(perfilEmpleadoDTO.getExperiencia());
        perfilEmpleado.setCertificaciones(perfilEmpleadoDTO.getCertificaciones());
        perfilEmpleado.setEliminar(false);

        try {
            perfilEmpleadoRepository.save(perfilEmpleado);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static class EmpleadoNoExisteException extends RuntimeException {
        public EmpleadoNoExisteException() {
            super("No existe Empleado");
        }
    }
    public PerfilEmpleado obtenerPerfilEmpleadoPorID(int id) {
        return perfilEmpleadoRepository.findById(id).orElse(null);
    }

    public List<PerfilEmpleado> obtenerTodosLosPerfilesDeEmpleados() {
        return perfilEmpleadoRepository.findAll();
    }
    public boolean eliminarPerfilEmpleado(int codigo) {
        Optional<PerfilEmpleado> perfilEmpleadoOptional = perfilEmpleadoRepository.findById(codigo);
        if (perfilEmpleadoOptional.isPresent()) {
            PerfilEmpleado perfilEmpleado = perfilEmpleadoOptional.get();
            perfilEmpleado.setEliminar(true);

            try {
                perfilEmpleadoRepository.save(perfilEmpleado);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }
}