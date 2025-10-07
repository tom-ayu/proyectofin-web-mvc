package com.udla.proyectofin_web_mvc.servicio;

import com.udla.proyectofin_web_mvc.modelo.Usuario;
import com.udla.proyectofin_web_mvc.repositorio.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Login
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // CRUD
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
