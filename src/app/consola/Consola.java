package programacion_2_trabajo_practico_2_marcoscassone02.src.app.consola;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.*;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.*;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificaciones;




public class Consola {
    private GestorUsuarios gestorUsuarios;
    private GestorRecursos gestorRecursos;
    private ServicioNotificaciones servicioNotificaciones;

    public Consola(GestorUsuarios gestorUsuarios, GestorRecursos gestorRecursos, ServicioNotificaciones servicioNotificaciones) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorRecursos = gestorRecursos;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void iniciar() {
        System.out.println("📚 Bienvenido al Sistema de Gestión de Biblioteca\n");

        gestorUsuarios.agregarUsuario(new Usuario("Marcos", 2, "marcoscassone02@egmail.com"));

        gestorRecursos.agregarRecurso(new Libro("El Principito", "Antoine"));
        gestorRecursos.agregarRecurso(new Revista("Mas Personas", 108));
        gestorRecursos.agregarRecurso(new Audiolibro("Cualquiera", "Pepe Voice"));

        System.out.println("📑 Recursos disponibles:");
        for (RecursoDigital recurso : gestorRecursos.obtenerRecursos()) {
            recurso.mostrarInformacion();
            recurso.visualizarEnConsola();

            if (recurso instanceof Prestable prestable) {
                prestable.prestar();
                System.out.println("✅ Recurso prestado");
            }

            if (recurso instanceof Renovable renovable && renovable.puedeRenovarse()) {
                renovable.renovar();
            }

            servicioNotificaciones.enviarNotificacion("Se accedió al recurso: " + recurso.getTitulo());
            System.out.println();
        }
    }
}

