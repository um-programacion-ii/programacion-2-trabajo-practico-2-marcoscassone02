package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion;

public class ServicioNotificaciones {
    public void enviarNotificacion(String mensaje) {
        enviarNotificacion(mensaje, TipoNotificacion.INFO);
    }

    public void enviarNotificacion(String mensaje, TipoNotificacion tipo) {
        switch (tipo) {
            case INFO -> System.out.println("[INFO] " + mensaje);
            case WARNING -> System.out.println("[ADVERTENCIA] " + mensaje);
            case ERROR -> System.out.println("[ERROR] " + mensaje);
        }
    }
}