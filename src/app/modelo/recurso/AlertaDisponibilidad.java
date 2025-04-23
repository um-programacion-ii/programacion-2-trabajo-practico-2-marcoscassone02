package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificaciones;
import java.util.Map;
import java.util.concurrent.*;

public class AlertaDisponibilidad {
    private final Map<RecursoDigital, BlockingQueue<Integer>> reservasPendientes;
    private final ServicioNotificaciones servicio;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AlertaDisponibilidad(Map<RecursoDigital, BlockingQueue<Integer>> reservasPendientes, ServicioNotificaciones servicio) {
        this.reservasPendientes = reservasPendientes;
        this.servicio = servicio;
    }

    public void iniciar() {
        executor.submit(() -> {
            while (true) {
                for (Map.Entry<RecursoDigital, BlockingQueue<Integer>> entry : reservasPendientes.entrySet()) {
                    RecursoDigital recurso = entry.getKey();
                    BlockingQueue<Integer> cola = entry.getValue();
                    if (recurso instanceof Prestable prestable && prestable.estaDisponible() && !cola.isEmpty()) {
                        int idUsuario = cola.poll();
                        servicio.enviarNotificacion("El recurso '" + recurso.getTitulo() + "' ya está disponible. Usuario ID: " + idUsuario + ", puede tomarlo ahora.");
                    }
                }
                Thread.sleep(5000); 
            }
        });
    }
}
