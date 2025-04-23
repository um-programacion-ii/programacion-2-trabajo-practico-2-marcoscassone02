package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificaciones;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.TipoNotificacion;
import java.util.List;
import java.util.concurrent.*;

public class AlertaRecordatorio {
    private final ServicioNotificaciones servicio;
    private final List<String> eventos;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public AlertaRecordatorio(List<String> eventos, ServicioNotificaciones servicio) {
        this.eventos = eventos;
        this.servicio = servicio;
    }

    public void iniciar() {
        scheduler.scheduleAtFixedRate(() -> {
            for (String evento : eventos) {
                servicio.enviarNotificacion("Recordatorio periódico: " + evento, TipoNotificacion.INFO);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
