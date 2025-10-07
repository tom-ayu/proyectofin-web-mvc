package com.udla.proyectofin_web_mvc.controlador;

import com.udla.proyectofin_web_mvc.modelo.Usuario;
import com.udla.proyectofin_web_mvc.servicio.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuarios/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        return "usuarios/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
}
