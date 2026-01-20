package com.udla.coremvc.api;

import com.udla.coremvc.modelo.Tarea;
import com.udla.coremvc.servicio.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaRestController {

    private final TareaService tareaService;

    public TareaRestController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas() {
        List<Tarea> tareas = tareaService.listarTareas();
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long id) {
        Tarea tarea = tareaService.buscarPorId(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarea);
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        tareaService.guardar(tarea);
        return ResponseEntity.ok(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        Tarea tareaExistente = tareaService.buscarPorId(id);
        if (tareaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        tarea.setId(id);
        tareaService.guardar(tarea);
        return ResponseEntity.ok(tarea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        Tarea tarea = tareaService.buscarPorId(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }

        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}