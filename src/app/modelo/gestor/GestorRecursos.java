package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.AlertaDisponibilidad;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.AlertaVencimiento;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.Categoria;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.Prestable;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.Prestamo;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.RecursoDigital;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.Reserva;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.excepcion.RecursoNoDisponibleException;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificaciones;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificacionesEmail;

public class GestorRecursos {
    private List<RecursoDigital> recursos = new ArrayList<>();
    private List<Prestamo> prestamos = new ArrayList<>();
    private Map<RecursoDigital, PriorityQueue<Reserva>> reservas = new HashMap<>();
    private ServicioNotificaciones servicioNotificaciones = new ServicioNotificacionesEmail();
    private ExecutorService executor = Executors.newFixedThreadPool(2);
    private Map<RecursoDigital, Integer> prestamosContador = new HashMap<>();
    private Map<Integer, Integer> prestamosPorUsuario = new HashMap<>();
    private AlertaVencimiento alertaVencimiento = new AlertaVencimiento(prestamos, servicioNotificaciones);
    private Map<RecursoDigital, BlockingQueue<Integer>> reservasPendientes = new ConcurrentHashMap<>();
    private AlertaDisponibilidad alertaDisponibilidad = new AlertaDisponibilidad(reservasPendientes, servicioNotificaciones);


    public void agregarRecurso(RecursoDigital recurso) {
        recursos.add(recurso);
    }

    public List<RecursoDigital> obtenerRecursos() {
        return recursos;
    }

    public List<RecursoDigital> buscarPorTitulo(String titulo) {
        return recursos.stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<RecursoDigital> filtrarPorCategoria(Categoria categoria) {
        return recursos.stream()
                .filter(r -> r.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<RecursoDigital> ordenarPorTitulo() {
        return recursos.stream()
                .sorted(Comparator.comparing(RecursoDigital::getTitulo))
                .collect(Collectors.toList());
    }
    
    public synchronized void prestarRecurso(RecursoDigital recurso, int idUsuario) throws RecursoNoDisponibleException {
        if (recurso instanceof Prestable prestable) {
            if (!prestable.estaDisponible()) {
                throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
            }
            prestable.prestar();
            prestamosContador.put(recurso, prestamosContador.getOrDefault(recurso, 0) + 1);
            prestamosPorUsuario.put(idUsuario, prestamosPorUsuario.getOrDefault(idUsuario, 0) + 1);
            prestamos.add(new Prestamo(recurso, idUsuario));
            System.out.println("Recurso prestado al usuario ID: " + idUsuario);
        } else {
            throw new RecursoNoDisponibleException("El recurso no admite préstamos.");
        }
    }

    public void prestarRecursoAUsuario(RecursoDigital recurso, int idUsuario) throws RecursoNoDisponibleException {
        prestarRecurso(recurso, idUsuario);
        prestamos.add(new Prestamo(recurso, idUsuario));
    }

    public synchronized void devolverRecurso(RecursoDigital recurso) {
        if (recurso instanceof Prestable prestable) {
            prestable.devolver();
            System.out.println("Recurso devuelto: " + recurso.getTitulo());
            procesarReservas(recurso);
        } else {
            System.out.println("Este recurso no admite devolución.");
        }
    }
    

    public List<Prestamo> obtenerPrestamos() {
        return prestamos;
    }
    public void reservarRecurso(RecursoDigital recurso, int idUsuario) {
        reservas.putIfAbsent(recurso, new PriorityQueue<>());
        reservas.get(recurso).offer(new Reserva(idUsuario));
    }
    
    public synchronized void procesarReservas(RecursoDigital recurso) {
        PriorityQueue<Reserva> cola = reservas.get(recurso);
        if (cola != null && !cola.isEmpty()) {
            Reserva siguiente = cola.poll();
            String mensaje = "Recurso disponible para usuario ID " + siguiente.getIdUsuario();
            executor.submit(() -> servicioNotificaciones.enviarNotificacion(mensaje));
        }
    }
    
    public void mostrarReservas(RecursoDigital recurso) {
        PriorityQueue<Reserva> cola = reservas.get(recurso);
        if (cola == null || cola.isEmpty()) {
            System.out.println("No hay reservas para este recurso.");
            return;
        }
    
        System.out.println("Reservas en cola:");
        for (Reserva r : cola) {
            System.out.println("Usuario ID: " + r.getIdUsuario() + " | Fecha: " + r.getFechaReserva());
        }
    }
    
    public void mostrarReportePrestamos() {
        System.out.println("Recursos más prestados:");
        prestamosContador.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .limit(5)
            .forEach(entry ->
                System.out.println("• " + entry.getKey().getTitulo() + " - " + entry.getValue() + " préstamos"));
    }
    
    public void mostrarReporteUsuariosActivos() {
        System.out.println("Usuarios más activos:");
        prestamosPorUsuario.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .limit(5)
            .forEach(entry ->
                System.out.println("• Usuario ID " + entry.getKey() + " - " + entry.getValue() + " préstamos"));
    }
    
    public void mostrarEstadisticasPorCategoria() {
        System.out.println("Estadísticas por categoría:");
        Map<Categoria, Long> estadisticas = prestamosContador.keySet().stream()
            .collect(Collectors.groupingBy(RecursoDigital::getCategoria, Collectors.counting()));
    
        estadisticas.forEach((categoria, cantidad) ->
            System.out.println("• " + categoria + ": " + cantidad + " recursos prestados"));
    }

    public GestorRecursos() {
        alertaVencimiento.iniciarMonitoreo();
        alertaDisponibilidad.iniciar();
    }
    
    
    
}