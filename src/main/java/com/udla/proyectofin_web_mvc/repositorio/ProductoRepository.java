package com.udla.proyectofin_web_mvc.repositorio;

import com.udla.proyectofin_web_mvc.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByUsuarioId(Long usuarioId);
}