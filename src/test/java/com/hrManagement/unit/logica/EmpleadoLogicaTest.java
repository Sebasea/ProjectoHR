package com.hrManagement.unit.logica;

import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.logica.EmpleadoLogica;
import com.hrManagement.logica.RolEnum;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.repository.EmpleadoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.hrManagement.logica.RolEnum.ADMINISTRADOR;
import static com.hrManagement.logica.RolEnum.GERENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles(profiles = "test")
class EmpleadoLogicaTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    private EmpleadoLogica empleadoLogica;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        empleadoLogica = new EmpleadoLogica(empleadoRepository);
    }

    @Test
    public void testGuardarEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setCodigo(1);
        empleadoDTO.setNombre("Juan Esteban");
        empleadoDTO.setEdad(30);
        empleadoDTO.setRol(RolEnum.ADMINISTRADOR);
        empleadoDTO.setEmail("juan.esteban@example.com");
        empleadoDTO.setNumeroTelefonico(555-1234);
        empleadoDTO.setResponsabilidades("Manejar departamento desarrollo");

        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(new Empleado());

        boolean result = empleadoLogica.guardarEmpleado(empleadoDTO);

        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
        Assertions.assertTrue(result);
    }

    @Test
    public void testGuardarEmpleadoAlreadyExists() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setCodigo(1);

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(new Empleado()));

        boolean result = empleadoLogica.guardarEmpleado(empleadoDTO);

        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, never()).save(any(Empleado.class));
        Assertions.assertFalse(result);
    }

    @Test
    public void testObtenerEmpleadoPorID() {
        Empleado empleado = new Empleado();
        empleado.setCodigo(1);

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        Empleado result = empleadoLogica.obtenerEmpleadoPorID(1);

        verify(empleadoRepository, times(1)).findById(1);
        Assertions.assertEquals(empleado, result);
    }

    @Test
    public void testObtenerEmpleadoPorIDNotFound() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        Empleado result = empleadoLogica.obtenerEmpleadoPorID(1);

        verify(empleadoRepository, times(1)).findById(1);
        Assertions.assertNull(result);
    }

    @Test
    public void testObtenerEmpleadosPorCargo() {
        List<Empleado> empleados = new ArrayList<>();
        Empleado empleado1 = new Empleado();
        empleado1.setRol(RolEnum.ADMINISTRADOR);
        Empleado empleado2 = new Empleado();
        empleado2.setRol(RolEnum.GERENTE);
        empleados.add(empleado1);
        empleados.add(empleado2);

        when(empleadoRepository.findByRol(RolEnum.ADMINISTRADOR)).thenReturn(empleados);

        List<Empleado> result = empleadoLogica.obtenerEmpleadosPorCargo(RolEnum.ADMINISTRADOR);

        verify(empleadoRepository, times(1)).findByRol(RolEnum.ADMINISTRADOR);
        Assertions.assertEquals(empleados, result);
    }

    @Test
    public void testObtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        Empleado empleado1 = new Empleado();
        Empleado empleado2 = new Empleado();
        empleados.add(empleado1);
        empleados.add(empleado2);

        when(empleadoRepository.findAll()).thenReturn(empleados);

        List<Empleado> result = empleadoLogica.obtenerTodosLosEmpleados();

        verify(empleadoRepository, times(1)).findAll();
        Assertions.assertEquals(empleados, result);
    }

    @Test
    public void testModificarEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNombre("Juan Esteban");
        empleadoDTO.setEdad(30);
        empleadoDTO.setRol(RolEnum.ADMINISTRADOR);
        empleadoDTO.setEmail("juan.esteban@example.com");
        empleadoDTO.setNumeroTelefonico(300-614-6088);
        empleadoDTO.setResponsabilidades("Manejar departamento desarrollo");

        Empleado empleado = new Empleado();
        empleado.setCodigo(1);
        empleado.setNombre("Juan Sebastian");
        empleado.setEdad(25);
        empleado.setRol(RolEnum.GERENTE);
        empleado.setEmail("juan.sebastian@example.com");
        empleado.setNumeroTelefonico(312-815-7562);
        empleado.setResponsabilidades("Scrum master");

        when(empleadoRepository.existsById(1)).thenReturn(true);
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(new Empleado());

        boolean result = empleadoLogica.modificarEmpleado(1, empleadoDTO);

        verify(empleadoRepository, times(1)).existsById(1);
        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
        Assertions.assertTrue(result);
        Assertions.assertEquals(empleadoDTO.getNombre(), empleado.getNombre());
        Assertions.assertEquals(empleadoDTO.getEdad(), empleado.getEdad());
        Assertions.assertEquals(empleadoDTO.getRol(), empleado.getRol());
        Assertions.assertEquals(empleadoDTO.getEmail(), empleado.getEmail());
        Assertions.assertEquals(empleadoDTO.getNumeroTelefonico(), empleado.getNumeroTelefonico());
        Assertions.assertEquals(empleadoDTO.getResponsabilidades(), empleado.getResponsabilidades());
    }

    @Test
    public void testModificarEmpleadoNotFound() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        when(empleadoRepository.existsById(1)).thenReturn(false);

        boolean result = empleadoLogica.modificarEmpleado(1, empleadoDTO);

        verify(empleadoRepository, times(1)).existsById(1);
        verify(empleadoRepository, never()).findById(anyInt());
        verify(empleadoRepository, never()).save(any(Empleado.class));
        Assertions.assertFalse(result);
    }

    @Test
    public void testEliminarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setCodigo(1);

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(new Empleado());

        boolean result = empleadoLogica.eliminarEmpleado(1);

        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
        Assertions.assertTrue(result);
        Assertions.assertTrue(empleado.isEliminar());
    }

    @Test
    public void testEliminarEmpleadoNotFound() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = empleadoLogica.eliminarEmpleado(1);

        verify(empleadoRepository, times(1)).findById(1);
        verify(empleadoRepository, never()).save(any(Empleado.class));
        Assertions.assertFalse(result);
    }}