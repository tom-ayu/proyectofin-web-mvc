package com.udla.coremvc.api;

import com.udla.coremvc.dto.EstadisticasDTO;
import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.modelo.Tarea;
import com.udla.coremvc.servicio.IProyectoService;
import com.udla.coremvc.servicio.TareaService;
import com.udla.coremvc.servicio.analisis.AnalizadorRiesgos;
import com.udla.coremvc.servicio.estrategias.EficienciaStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasRestController {

    private final IProyectoService proyectoService;
    private final TareaService tareaService;
    private final AnalizadorRiesgos analizadorRiesgos;
    private final EficienciaStrategy eficienciaStrategy;

    public EstadisticasRestController(IProyectoService proyectoService,
                                      TareaService tareaService,
                                      AnalizadorRiesgos analizadorRiesgos,
                                      EficienciaStrategy eficienciaStrategy) {
        this.proyectoService = proyectoService;
        this.tareaService = tareaService;
        this.analizadorRiesgos = analizadorRiesgos;
        this.eficienciaStrategy = eficienciaStrategy;
    }

    @GetMapping
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas() {
        EstadisticasDTO estadisticas = new EstadisticasDTO();

        // Estadísticas de proyectos
        List<Proyecto> proyectos = proyectoService.listarProyectos();
        estadisticas.setTotalProyectos((long) proyectos.size());

        long proyectosEnRiesgo = proyectos.stream()
                .filter(p -> analizadorRiesgos.analizarRiesgo(p))
                .count();
        estadisticas.setProyectosEnRiesgo(proyectosEnRiesgo);
        estadisticas.setProyectosSinRiesgo(estadisticas.getTotalProyectos() - proyectosEnRiesgo);

        double presupuestoTotal = proyectos.stream()
                .mapToDouble(p -> p.getPresupuestoTotal() != null ? p.getPresupuestoTotal() : 0.0)
                .sum();
        estadisticas.setPresupuestoTotalInvertido(presupuestoTotal);

        double eficienciaPromedio = proyectos.stream()
                .mapToDouble(p -> eficienciaStrategy.calcular(p))
                .average()
                .orElse(0.0);
        estadisticas.setEficienciaPromedio(eficienciaPromedio);

        // Estadísticas de tareas
        List<Tarea> tareas = tareaService.listarTareas();
        estadisticas.setTotalTareas((long) tareas.size());

        long tareasPendientes = tareas.stream()
                .filter(t -> "TO_DO".equals(t.getEstado()))
                .count();
        estadisticas.setTareasPendientes(tareasPendientes);

        long tareasEnProgreso = tareas.stream()
                .filter(t -> "IN_PROGRESS".equals(t.getEstado()) || "QA_REVIEW".equals(t.getEstado()))
                .count();
        estadisticas.setTareasEnProgreso(tareasEnProgreso);

        long tareasCompletadas = tareas.stream()
                .filter(t -> "DONE".equals(t.getEstado()))
                .count();
        estadisticas.setTareasCompletadas(tareasCompletadas);

        return ResponseEntity.ok(estadisticas);
    }
}