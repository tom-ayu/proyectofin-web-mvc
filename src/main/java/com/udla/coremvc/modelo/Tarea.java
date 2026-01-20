package com.udla.coremvc.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull(message = "El estimado original es obligatorio")
    @Min(value = 1, message = "El estimado original debe ser mayor a 0")
    @Column(name = "original_estimate", nullable = false)
    private Integer originalEstimate;

    @NotNull(message = "El tiempo real es obligatorio")
    @Min(value = 0, message = "El tiempo real no puede ser negativo")
    @Column(name = "tiempo_real", nullable = false)
    private Integer tiempoReal;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "TO_DO|IN_PROGRESS|QA_REVIEW|DONE|REOPENED",
            message = "Estado debe ser: TO_DO, IN_PROGRESS, QA_REVIEW, DONE o REOPENED")
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotBlank(message = "La prioridad es obligatoria")
    @Column(name = "prioridad", nullable = false)
    private String prioridad; // LOW, MEDIUM, HIGH

    @Min(value = 0, message = "Veces reabierta no puede ser negativo")
    @Column(name = "veces_reabierta")
    private Integer vecesReabierta = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    @NotNull(message = "Debe seleccionar un proyecto")
    private Proyecto proyecto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    @NotNull(message = "Debe seleccionar un recurso")
    private Recurso recurso;

    public Tarea() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getOriginalEstimate() {
        return originalEstimate;
    }

    public void setOriginalEstimate(Integer originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    public Integer getTiempoReal() {
        return tiempoReal;
    }

    public void setTiempoReal(Integer tiempoReal) {
        this.tiempoReal = tiempoReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getVecesReabierta() {
        return vecesReabierta;
    }

    public void setVecesReabierta(Integer vecesReabierta) {
        this.vecesReabierta = vecesReabierta;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }


    public Double calcularTiempoAjustado() {
        if (this.proyecto == null || this.tiempoReal == null || this.originalEstimate == null) {
            return 0.0;
        }
        // Protección contra vecesReabierta null
        int veces = (this.vecesReabierta != null) ? this.vecesReabierta : 0;

        double penalizacion = veces * this.originalEstimate * this.proyecto.getPorcentajeQA();
        return this.tiempoReal + penalizacion;
    }

    public Double calcularEficiencia() {
        Double tiempoAjustado = calcularTiempoAjustado();

        if (tiempoAjustado == null || tiempoAjustado == 0.0 || this.originalEstimate == null) {
            return 0.0;
        }

        return this.originalEstimate / tiempoAjustado;
    }

    // Metodo para reabir tarea. Suma el contador y cambia el estado correspondiente a la tarea.
    public void reabrirTarea() {
        this.vecesReabierta++;
        this.estado = "REOPENED";
    }
}