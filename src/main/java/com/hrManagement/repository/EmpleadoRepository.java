package com.hrManagement.repository;

import com.hrManagement.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    List<Empleado> findByRol(Enum rol);
}