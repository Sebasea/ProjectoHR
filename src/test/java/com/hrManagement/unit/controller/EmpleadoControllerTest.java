package com.hrManagement.unit.controller;

import com.hrManagement.controller.EmpleadoController;
import com.hrManagement.controller.dto.EmpleadoDTO;
import com.hrManagement.logica.EmpleadoLogica;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.hrManagement.logica.RolEnum.ADMINISTRADOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles(profiles = "test")
class EmpleadoControllerTest {

    @Mock
    private EmpleadoLogica empleadoLogica;

    @Mock
    private EmpleadoRepository empleadoRepository;

    private EmpleadoController empleadoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        empleadoController = new EmpleadoController(empleadoLogica, empleadoRepository);
    }

    @Test
    public void testAgregarEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        when(empleadoLogica.guardarEmpleado(any(EmpleadoDTO.class))).thenReturn(true);
        ResponseEntity<String> response = empleadoController.agregarEmpleado(empleadoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Empleado agregado correctamente", response.getBody());
    }

    @Test
    public void testObtenerDatosEmpleadoPorID() {
        Empleado empleado = new Empleado();
        when(empleadoLogica.obtenerEmpleadoPorID(anyInt())).thenReturn(empleado);
        ResponseEntity<Empleado> response = empleadoController.obtenerDatosEmpleadoPorID(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    public void testObtenerEmpleadosPorCargo() {
        List<Empleado> empleados = new ArrayList<>();
        when(empleadoLogica.obtenerEmpleadosPorCargo(any())).thenReturn(empleados);
        List<Empleado> response = empleadoController.obtenerEmpleadosPorCargo(ADMINISTRADOR);
        assertEquals(empleados, response);
    }

    @Test
    public void testObtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        when(empleadoLogica.obtenerTodosLosEmpleados()).thenReturn(empleados);
        List<Empleado> response = empleadoController.obtenerTodosLosEmpleados();
        assertEquals(empleados, response);
    }

    @Test
    public void testModificarEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        when(empleadoLogica.modificarEmpleado(anyInt(), any(EmpleadoDTO.class))).thenReturn(true);
        ResponseEntity<String> response = empleadoController.modificarEmpleado(1, empleadoDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Empleado actualizado correctamente", response.getBody());
    }

    @Test
    public void testEliminarEmpleado() throws EmpleadoLogica.EmpleadoNoExisteException {
        when(empleadoLogica.eliminarEmpleado(anyInt())).thenReturn(true);
        ResponseEntity<String> response = empleadoController.eliminarEmpleado(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Empleado desactivado correctamente", response.getBody());
    }
}
