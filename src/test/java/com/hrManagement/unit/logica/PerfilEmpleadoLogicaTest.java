package com.hrManagement.unit.logica;

import com.hrManagement.controller.dto.PerfilEmpleadoDTO;
import com.hrManagement.logica.PerfilEmpleadoLogica;
import com.hrManagement.modelo.Empleado;
import com.hrManagement.modelo.PerfilEmpleado;
import com.hrManagement.repository.EmpleadoRepository;
import com.hrManagement.repository.PerfilEmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
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
class PerfilEmpleadoLogicaTest {

    private PerfilEmpleadoLogica perfilEmpleadoLogica;

    @Mock
    private PerfilEmpleadoRepository perfilEmpleadoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        perfilEmpleadoLogica = new PerfilEmpleadoLogica(perfilEmpleadoRepository, empleadoRepository);
    }
    @Test
    public void testGuardarPerfilEmpleado() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("Juan Pabloo");
        perfilEmpleadoDTO.setHabilidades("Java, Spring");
        perfilEmpleadoDTO.setExperiencia("5 years");
        perfilEmpleadoDTO.setCertificaciones("Oracle Certified Professional");

        Empleado empleado = new Empleado();
        empleado.setCodigo(1);

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(perfilEmpleadoRepository.save(any(PerfilEmpleado.class))).thenReturn(new PerfilEmpleado());

        boolean result = perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);

        assertTrue(result);
        verify(perfilEmpleadoRepository, times(1)).save(any(PerfilEmpleado.class));
    }

    @Test
    public void testGuardarPerfilEmpleadoWithEmptyNombre() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("");
        perfilEmpleadoDTO.setHabilidades("Java, Spring");
        perfilEmpleadoDTO.setExperiencia("5 years");
        perfilEmpleadoDTO.setCertificaciones("Oracle Certified Professional");

        assertThrows(PerfilEmpleadoLogica.CamposVaciosException.class, () -> {
            perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);
        });
    }

    @Test
    public void testGuardarPerfilEmpleadoWithEmptyHabilidades() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("Juan Pablo");
        perfilEmpleadoDTO.setHabilidades("");
        perfilEmpleadoDTO.setExperiencia("5 years");
        perfilEmpleadoDTO.setCertificaciones("Oracle Certified Professional");

        assertThrows(PerfilEmpleadoLogica.CamposVaciosException.class, () -> {
            perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);
        });
    }

    @Test
    public void testGuardarPerfilEmpleadoWithEmptyExperiencia() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("Juan Pablo");
        perfilEmpleadoDTO.setHabilidades("Java, Spring");
        perfilEmpleadoDTO.setExperiencia("");
        perfilEmpleadoDTO.setCertificaciones("Oracle Certified Professional");

        assertThrows(PerfilEmpleadoLogica.CamposVaciosException.class, () -> {
            perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);
        });
    }

    @Test
    public void testGuardarPerfilEmpleadoWithEmptyCertificaciones() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("Juan Pablo");
        perfilEmpleadoDTO.setHabilidades("Java, Spring");
        perfilEmpleadoDTO.setExperiencia("5 years");
        perfilEmpleadoDTO.setCertificaciones("");

        assertThrows(PerfilEmpleadoLogica.CamposVaciosException.class, () -> {
            perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);
        });
    }

    @Test
    public void testGuardarPerfilEmpleadoWithNonexistentEmpleado() {
        PerfilEmpleadoDTO perfilEmpleadoDTO = new PerfilEmpleadoDTO();
        perfilEmpleadoDTO.setCodigo(1);
        perfilEmpleadoDTO.setNombre("Juan Pablo");
        perfilEmpleadoDTO.setHabilidades("Java, Spring");
        perfilEmpleadoDTO.setExperiencia("5 years");
        perfilEmpleadoDTO.setCertificaciones("Oracle Certified Professional");

        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PerfilEmpleadoLogica.EmpleadoNoExisteException.class, () -> {
            perfilEmpleadoLogica.guardarPerfilEmpleado(perfilEmpleadoDTO);
        });
    }
    @Test
    public void testObtenerPerfilEmpleadoPorID() {
        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(1);

        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.of(perfilEmpleado));

        PerfilEmpleado result = perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(1);

        assertNotNull(result);
        assertEquals(1, result.getCodigo());
    }

    @Test
    public void testObtenerPerfilEmpleadoPorIDWithNonexistentID() {
        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.empty());

        PerfilEmpleado result = perfilEmpleadoLogica.obtenerPerfilEmpleadoPorID(1);

        assertNull(result);
    }

    @Test
    public void testObtenerTodosLosPerfilesDeEmpleados() {
        List<PerfilEmpleado> perfilEmpleadoList = new ArrayList<>();
        perfilEmpleadoList.add(new PerfilEmpleado());
        perfilEmpleadoList.add(new PerfilEmpleado());

        when(perfilEmpleadoRepository.findAll()).thenReturn(perfilEmpleadoList);

        List<PerfilEmpleado> result = perfilEmpleadoLogica.obtenerTodosLosPerfilesDeEmpleados();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testEliminarPerfilEmpleado() {
        PerfilEmpleado perfilEmpleado = new PerfilEmpleado();
        perfilEmpleado.setCodigo(1);

        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.of(perfilEmpleado));
        when(perfilEmpleadoRepository.save(any(PerfilEmpleado.class))).thenReturn(new PerfilEmpleado());

        boolean result = perfilEmpleadoLogica.eliminarPerfilEmpleado(1);

        assertTrue(result);
        assertTrue(perfilEmpleado.isEliminar());
        verify(perfilEmpleadoRepository, times(1)).save(any(PerfilEmpleado.class));
    }

    @Test
    public void testEliminarPerfilEmpleadoWithNonexistentID() {
        when(perfilEmpleadoRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = perfilEmpleadoLogica.eliminarPerfilEmpleado(1);

        assertFalse(result);
        verify(perfilEmpleadoRepository, never()).save(any(PerfilEmpleado.class));
    }
}