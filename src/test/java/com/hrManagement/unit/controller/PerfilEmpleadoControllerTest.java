package com.hrManagement.unit.controller;


import com.hrManagement.controller.PerfilEmpleadoController;
import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.logica.PerfilEmpleadoLogica;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles(profiles = "test")
class PerfilEmpleadoControllerTest {

    @Mock
    private PerfilEmpleadoLogica perfilEmpleadoLogica;

    @Mock
    private PerfilEmpleadoRepository perfilEmpleadoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    private PerfilEmpleadoController perfilEmpleadoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        perfilEmpleadoController = new PerfilEmpleadoController(perfilEmpleadoLogica, perfilEmpleadoRepository, empleadoRepository);
    }

    @Test
    public void agregarPerfilEmpleado_Success() throws PerfilEmpleadoLogica.EmpleadoNoExisteException {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("John Doe");
        perfilEmpleadoDTO.setHabilidades("Java, Spring");
        perfilEmpleadoDTO.setExperiencia("5 años");
        perfilEmpleadoDTO.setCertificaciones("Oracle Certified Professional Java SE 11 Developer");

        when(perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO)).thenReturn(true);

        ResponseEntity<String> result = perfilEmpleadoController.agregarPerfilEmpleado(perfilEmpleadoDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Perfil de empleado agregado correctamente", result.getBody());

        verify(perfilEmpleadoLogica, times(1)).guardarPerfilEmpleado(perfilEmpleadoDTO);
    }

    @Test
    public void agregarPerfilEmpleado_EmpleadoNoExisteException() throws PerfilEmpleadoLogica.EmpleadoNoExisteException {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);

        when(perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO)).thenThrow(new PerfilEmpleadoLogica.EmpleadoNoExisteException());

        ResponseEntity<String> result = perfilEmpleadoController.agregarPerfilEmpleado(perfilEmpleadoDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("No se puede crear el perfil ya que no hay un empleado con ese código", result.getBody());

        verify(perfilEmpleadoLogica, times(1)).guardarPerfilEmpleado(perfilEmpleadoDTO);
    }

    @Test
    public void agregarPerfilEmpleado_InternalServerError() throws PerfilEmpleadoLogica.EmpleadoNoExisteException {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);

        when(perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO)).thenReturn(false);

        ResponseEntity<String> result = perfilEmpleadoController.agregarPerfilEmpleado(perfilEmpleadoDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("No se pudo agregar el perfil de empleado.", result.getBody());

        verify(perfilEmpleadoLogica, times(1)).guardarPerfilEmpleado(perfilEmpleadoDTO);
    }

    @Test
    public void obtenerDatosEmpleadoPorID_Success() {
        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(1);
        perfilEmpleado.setNombre("John Doe");
        perfilEmpleado.setHabilidades("Java, Spring");
        perfilEmpleado.setExperiencia("5 años");
        perfilEmpleado.setCertificaciones("Oracle Certified Professional Java SE 11 Developer");

        when(perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(1)).thenReturn(perfilEmpleado);

        ResponseEntity<PerfilEmpleado> result = perfilEmpleadoController.obtenerDatosEmpleadoPorID(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(perfilEmpleado, result.getBody());

        verify(perfilEmpleadoLogica, times(1)).obtenerPerfilEmpleadoPorID(1);
    }

    @Test
    public void obtenerDatosEmpleadoPorID_NotFound() {
        when(perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(1)).thenReturn(null);

        ResponseEntity<PerfilEmpleado> result = perfilEmpleadoController.obtenerDatosEmpleadoPorID(1);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        verify(perfilEmpleadoLogica, times(1)).obtenerPerfilEmpleadoPorID(1);
    }

    @Test
    public void obtenerTodosLosPerfilesDeEmpleados_Success() {
        List<PerfilEmpleado> perfilesEmpleado = new ArrayList<>();
        PerfilEmpleado perfilEmpleado1 = new PerfilEmpleado();
        perfilEmpleado1.setCodigo(1);
        perfilEmpleado1.setNombre("John Doe");
        perfilEmpleado1.setHabilidades("Java, Spring");
        perfilEmpleado1.setExperiencia("5 años");
        perfilEmpleado1.setCertificaciones("Oracle Certified Professional Java SE 11 Developer");
        perfilesEmpleado.add(perfilEmpleado1);
        PerfilEmpleado perfilEmpleado2 = new PerfilEmpleado();
        perfilEmpleado2.setCodigo(2);
        perfilEmpleado2.setNombre("Jane Doe");
        perfilEmpleado2.setHabilidades("Python, Django");
        perfilEmpleado2.setExperiencia("3 años");
        perfilEmpleado2.setCertificaciones("AWS Certified Solutions Architect - Associate");
        perfilesEmpleado.add(perfilEmpleado2);

        when(perfilEmpleadoLogica.obtenerTodosLosPerfilesDeEmpleados()).thenReturn(perfilesEmpleado);

        List<PerfilEmpleado> result = perfilEmpleadoController.obtenerTodosLosPerfilesDeEmpleados();

        assertNotNull(result);
        assertEquals(perfilesEmpleado, result);

        verify(perfilEmpleadoLogica, times(1)).obtenerTodosLosPerfilesDeEmpleados();
    }

    @Test
    public void eliminarPerfilEmpleado_Success() throws PerfilEmpleadoLogica.EmpleadoNoExisteException {
        when(perfilEmpleadoLogica.eliminarPerfilEmpleado(1)).thenReturn(true);

        ResponseEntity<String> result = perfilEmpleadoController.eliminarPerfilEmpleado(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Perfil del empleado desactivado correctamente", result.getBody());

        verify(perfilEmpleadoLogica, times(1)).eliminarPerfilEmpleado(1);
    }

    @Test
    public void eliminarPerfilEmpleado_EmpleadoNoExisteException() throws PerfilEmpleadoLogica.EmpleadoNoExisteException {
        when(perfilEmpleadoLogica.eliminarPerfilEmpleado(1)).thenThrow(new PerfilEmpleadoLogica.EmpleadoNoExisteException());

        ResponseEntity<String> result = perfilEmpleadoController.eliminarPerfilEmpleado(1);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("No se puede desactivar el perfil del empleado ya que no existe.", result.getBody());

        verify(perfilEmpleadoLogica, times(1)).eliminarPerfilEmpleado(1);
    }

    @Test
    public void eliminarPerfilEmpleado_InternalServerError() throws PerfilEmpleadoLogica.EmpleadoNoExisteException {
        when(perfilEmpleadoLogica.eliminarPerfilEmpleado(1)).thenReturn(false);

        ResponseEntity<String> result = perfilEmpleadoController.eliminarPerfilEmpleado(1);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("No se pudo desactivar el perfil del empleado.", result.getBody());

        verify(perfilEmpleadoLogica, times(1)).eliminarPerfilEmpleado(1);
    }

}