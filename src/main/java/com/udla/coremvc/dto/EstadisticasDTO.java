package com.udla.coremvc.dto;

public class EstadisticasDTO {

    private Long totalProyectos;
    private Long proyectosEnRiesgo;
    private Long proyectosSinRiesgo;
    private Double presupuestoTotalInvertido;
    private Double eficienciaPromedio;
    private Long totalTareas;
    private Long tareasPendientes;
    private Long tareasEnProgreso;
    private Long tareasCompletadas;

    public EstadisticasDTO() {
    }

    // Getters y Setters
    public Long getTotalProyectos() {
        return totalProyectos;
    }

    public void setTotalProyectos(Long totalProyectos) {
        this.totalProyectos = totalProyectos;
    }

    public Long getProyectosEnRiesgo() {
        return proyectosEnRiesgo;
    }

    public void setProyectosEnRiesgo(Long proyectosEnRiesgo) {
        this.proyectosEnRiesgo = proyectosEnRiesgo;
    }

    public Long getProyectosSinRiesgo() {
        return proyectosSinRiesgo;
    }

    public void setProyectosSinRiesgo(Long proyectosSinRiesgo) {
        this.proyectosSinRiesgo = proyectosSinRiesgo;
    }

    public Double getPresupuestoTotalInvertido() {
        return presupuestoTotalInvertido;
    }

    public void setPresupuestoTotalInvertido(Double presupuestoTotalInvertido) {
        this.presupuestoTotalInvertido = presupuestoTotalInvertido;
    }

    public Double getEficienciaPromedio() {
        return eficienciaPromedio;
    }

    public void setEficienciaPromedio(Double eficienciaPromedio) {
        this.eficienciaPromedio = eficienciaPromedio;
    }

    public Long getTotalTareas() {
        return totalTareas;
    }

    public void setTotalTareas(Long totalTareas) {
        this.totalTareas = totalTareas;
    }

    public Long getTareasPendientes() {
        return tareasPendientes;
    }

    public void setTareasPendientes(Long tareasPendientes) {
        this.tareasPendientes = tareasPendientes;
    }

    public Long getTareasEnProgreso() {
        return tareasEnProgreso;
    }

    public void setTareasEnProgreso(Long tareasEnProgreso) {
        this.tareasEnProgreso = tareasEnProgreso;
    }

    public Long getTareasCompletadas() {
        return tareasCompletadas;
    }

    public void setTareasCompletadas(Long tareasCompletadas) {
        this.tareasCompletadas = tareasCompletadas;
    }
}