package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion;

public class ServicioNotificacionesSMS implements ServicioNotificaciones {
    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("SMS: " + mensaje);
    }
}
