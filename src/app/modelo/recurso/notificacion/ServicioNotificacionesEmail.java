package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {
    public void enviarNotificacion(String mensaje) {
        System.out.println("📧 Notificación por Email: " + mensaje);
    }
}
