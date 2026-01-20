package com.udla.coremvc.api;

import com.udla.coremvc.modelo.Recurso;
import com.udla.coremvc.servicio.RecursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@CrossOrigin(origins = "*")
public class RecursoRestController {

    private final RecursoService recursoService;

    public RecursoRestController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @GetMapping
    public ResponseEntity<List<Recurso>> listarRecursos() {
        List<Recurso> recursos = recursoService.listarRecursos();
        return ResponseEntity.ok(recursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recurso> obtenerRecurso(@PathVariable Long id) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    public ResponseEntity<Recurso> crearRecurso(@RequestBody Recurso recurso) {
        recursoService.guardar(recurso);
        return ResponseEntity.ok(recurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recurso> actualizarRecurso(@PathVariable Long id, @RequestBody Recurso recurso) {
        Recurso recursoExistente = recursoService.buscarPorId(id);
        if (recursoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        recurso.setId(id);
        recursoService.guardar(recurso);
        return ResponseEntity.ok(recurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long id) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return ResponseEntity.notFound().build();
        }

        recursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}