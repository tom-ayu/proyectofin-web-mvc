package com.udla.coremvc.config;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.modelo.Recurso;
import com.udla.coremvc.modelo.Tarea;
import com.udla.coremvc.modelo.Usuario;
import com.udla.coremvc.repositorio.ProyectoRepository;
import com.udla.coremvc.repositorio.RecursoRepository;
import com.udla.coremvc.repositorio.TareaRepository;
import com.udla.coremvc.repositorio.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("prod")
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;
    private final RecursoRepository recursoRepository;
    private final TareaRepository tareaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           ProyectoRepository proyectoRepository,
                           RecursoRepository recursoRepository,
                           TareaRepository tareaRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.proyectoRepository = proyectoRepository;
        this.recursoRepository = recursoRepository;
        this.tareaRepository = tareaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        initUsuarios();
        initRecursos();
        initProyectos();
        initTareas();
    }

    private void initUsuarios() {
        if (usuarioRepository.findByUsername("admin") == null) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setRol("ADMIN");
            admin.setEnabled(true);
            usuarioRepository.save(admin);
            System.out.println("------- Usuario ADMIN creado -------");
        }
        if (usuarioRepository.findByUsername("usuario") == null) {
            Usuario user = new Usuario();
            user.setUsername("usuario");
            user.setPassword(passwordEncoder.encode("User123!"));
            user.setRol("USER");
            user.setEnabled(true);
            usuarioRepository.save(user);
            System.out.println("------- Usuario USER creado -------");
        }
    }

    private void initRecursos() {
        if (recursoRepository.count() == 0) {
            Recurso r1 = new Recurso();
            r1.setNombre("Juan Pérez");
            r1.setRol("Desarrollador Sr.");
            r1.setHorasDisponibles(160);
            r1.setCostoHora(35.0);
            recursoRepository.save(r1);
            Recurso r2 = new Recurso();
            r2.setNombre("María García");
            r2.setRol("Desarrollador Jr.");
            r2.setHorasDisponibles(160);
            r2.setCostoHora(20.0);
            recursoRepository.save(r2);
            Recurso r3 = new Recurso();
            r3.setNombre("Carlos López");
            r3.setRol("QA Tester");
            r3.setHorasDisponibles(160);
            r3.setCostoHora(25.0);
            recursoRepository.save(r3);

            System.out.println("------- Recursos creados -------");
        }
    }

    private void initProyectos() {
        if (proyectoRepository.count() == 0) {
            Proyecto p1 = new Proyecto();
            p1.setNombre("Sistema de Ventas");
            p1.setPresupuestoTotal(50000.0);
            p1.setHorasEstimadas(500);
            p1.setPorcentajeQA(0.25);
            proyectoRepository.save(p1);
            Proyecto p2 = new Proyecto();
            p2.setNombre("App Móvil Inventario");
            p2.setPresupuestoTotal(30000.0);
            p2.setHorasEstimadas(300);
            p2.setPorcentajeQA(0.20);
            proyectoRepository.save(p2);

            System.out.println("------- Proyectos creados -------");
        }
    }

    private void initTareas() {
        if (tareaRepository.count() == 0) {
            Proyecto proyecto = proyectoRepository.findById(1L).orElse(null);
            Recurso recurso1 = recursoRepository.findById(1L).orElse(null);
            Recurso recurso2 = recursoRepository.findById(2L).orElse(null);
            Recurso recurso3 = recursoRepository.findById(3L).orElse(null);

            if (proyecto != null && recurso1 != null) {
                Tarea t1 = new Tarea();
                t1.setTitulo("Diseñar mockups de login");
                t1.setOriginalEstimate(12);
                t1.setTiempoReal(28);
                t1.setEstado("DONE");
                t1.setPrioridad("HIGH");
                t1.setVecesReabierta(2);
                t1.setProyecto(proyecto);
                t1.setRecurso(recurso1);
                tareaRepository.save(t1);
                Tarea t2 = new Tarea();
                t2.setTitulo("Implementar autenticación");
                t2.setOriginalEstimate(20);
                t2.setTiempoReal(35);
                t2.setEstado("DONE");
                t2.setPrioridad("HIGH");
                t2.setVecesReabierta(3);
                t2.setProyecto(proyecto);
                t2.setRecurso(recurso1);
                tareaRepository.save(t2);
                Tarea t3 = new Tarea();
                t3.setTitulo("Testing módulo usuarios");
                t3.setOriginalEstimate(10);
                t3.setTiempoReal(8);
                t3.setEstado("DONE");
                t3.setPrioridad("MEDIUM");
                t3.setVecesReabierta(0);
                t3.setProyecto(proyecto);
                t3.setRecurso(recurso3);
                tareaRepository.save(t3);
                Tarea t4 = new Tarea();
                t4.setTitulo("API REST para productos");
                t4.setOriginalEstimate(30);
                t4.setTiempoReal(25);
                t4.setEstado("IN_PROGRESS");
                t4.setPrioridad("HIGH");
                t4.setVecesReabierta(0);
                t4.setProyecto(proyecto);
                t4.setRecurso(recurso2);
                tareaRepository.save(t4);
                Tarea t5 = new Tarea();
                t5.setTitulo("Validaciones de formularios");
                t5.setOriginalEstimate(16);
                t5.setTiempoReal(20);
                t5.setEstado("QA_REVIEW");
                t5.setPrioridad("MEDIUM");
                t5.setVecesReabierta(1);
                t5.setProyecto(proyecto);
                t5.setRecurso(recurso2);
                tareaRepository.save(t5);

                System.out.println("------- Tareas creadas -------");
            }
        }
    }
}