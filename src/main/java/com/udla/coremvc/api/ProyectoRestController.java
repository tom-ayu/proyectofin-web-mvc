package com.udla.coremvc.api;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.servicio.IProyectoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.udla.coremvc.modelo.ReporteProyecto;
import com.udla.coremvc.servicio.GeneradorReportes;
import com.udla.coremvc.servicio.analisis.AnalizadorRiesgos;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoRestController {

    private final IProyectoService proyectoService;
    private final GeneradorReportes generadorReportes;
    private final AnalizadorRiesgos analizadorRiesgos;

    public ProyectoRestController(IProyectoService proyectoService,
                                  GeneradorReportes generadorReportes,
                                  AnalizadorRiesgos analizadorRiesgos) {
        this.proyectoService = proyectoService;
        this.generadorReportes = generadorReportes;
        this.analizadorRiesgos = analizadorRiesgos;
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> listarProyectos(
            @RequestParam(required = false) String riesgo,
            @RequestParam(required = false) Double presupuestoMin,
            @RequestParam(required = false) Double presupuestoMax) {

        List<Proyecto> proyectos = proyectoService.listarProyectos();

        // Filtrar por riesgo
        if (riesgo != null) {
            proyectos = proyectos.stream()
                    .filter(p -> {
                        boolean enRiesgo = analizadorRiesgos.analizarRiesgo(p);
                        return (riesgo.equalsIgnoreCase("SI") && enRiesgo) ||
                                (riesgo.equalsIgnoreCase("NO") && !enRiesgo);
                    })
                    .toList();
        }

        // Filtrar por presupuesto mínimo
        if (presupuestoMin != null) {
            proyectos = proyectos.stream()
                    .filter(p -> p.getPresupuestoTotal() >= presupuestoMin)
                    .toList();
        }

        // Filtrar por presupuesto máximo
        if (presupuestoMax != null) {
            proyectos = proyectos.stream()
                    .filter(p -> p.getPresupuestoTotal() <= presupuestoMax)
                    .toList();
        }

        return ResponseEntity.ok(proyectos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.buscarPorId(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proyecto);
    }

    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        try {
            proyectoService.guardar(proyecto);
            return ResponseEntity.ok(proyecto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        Proyecto proyectoExistente = proyectoService.buscarPorId(id);
        if (proyectoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        proyecto.setId(id);
        try {
            proyectoService.guardar(proyecto);
            return ResponseEntity.ok(proyecto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.buscarPorId(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<ReporteProyecto> generarReporte(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.buscarPorId(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        ReporteProyecto reporte = generadorReportes.generarReporteCompleto(proyecto);
        return ResponseEntity.ok(reporte);
    }
}