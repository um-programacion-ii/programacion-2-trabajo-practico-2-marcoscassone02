package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificaciones;

public class AlertaVencimiento {
    private List<Prestamo> prestamos;
    private ServicioNotificaciones servicio;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public AlertaVencimiento(List<Prestamo> prestamos, ServicioNotificaciones servicio) {
        this.prestamos = prestamos;
        this.servicio = servicio;
    }

    public void iniciarMonitoreo() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalDate hoy = LocalDate.now();
            for (Prestamo p : prestamos) {
                long diasRestantes = ChronoUnit.DAYS.between(hoy, p.getFechaDevolucion());
                if (diasRestantes == 1) {
                    servicio.enviarNotificacion("Recordatorio: El recurso '" + p.getRecurso().getTitulo() + "' vence mañana.");
                } else if (diasRestantes == 0) {
                    servicio.enviarNotificacion("Alerta: El recurso '" + p.getRecurso().getTitulo() + "' vence hoy.");
                }
            }
        }, 0, 1, TimeUnit.DAYS);
    }
} 
