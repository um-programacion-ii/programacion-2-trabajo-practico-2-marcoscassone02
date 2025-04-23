package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion;

public class ServicioNotificacionesEmail extends ServicioNotificaciones {

    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("[EMAIL] " + mensaje);
    }

    @Override
    public void enviarNotificacion(String mensaje, TipoNotificacion tipo) {
        System.out.println("[EMAIL - " + tipo.name() + "] " + mensaje);
    }
}
