package com.udla.proyectofin_web_mvc.repositorio;

import com.udla.proyectofin_web_mvc.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
