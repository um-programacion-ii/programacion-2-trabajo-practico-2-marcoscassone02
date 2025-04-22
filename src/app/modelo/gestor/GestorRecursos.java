package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    
    public void prestarRecurso(RecursoDigital recurso) throws RecursoNoDisponibleException {
    if (recurso instanceof Prestable prestable) {
        if (prestable.estaPrestado()) {
            throw new RecursoNoDisponibleException("El recurso ya está prestado.");
        } else {
            prestable.prestar();
        }
    } else {
        throw new RecursoNoDisponibleException("Este recurso no se puede prestar.");
    }
    }

    public void prestarRecursoAUsuario(RecursoDigital recurso, int idUsuario) throws RecursoNoDisponibleException {
        prestarRecurso(recurso);
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
    
    public synchronized void prestarRecurso(RecursoDigital recurso, int idUsuario) throws RecursoNoDisponibleException {
        if (recurso instanceof Prestable prestable) {
            if (!prestable.estaDisponible()) {
                throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
            }
            prestable.prestar();
            System.out.println("Recurso prestado al usuario ID: " + idUsuario);
        } else {
            throw new RecursoNoDisponibleException("El recurso no admite préstamos.");
        }
    }
    
    
    
}