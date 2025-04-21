package programacion_2_trabajo_practico_2_marcoscassone02.src.app;  

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.RecursoDigital;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.GestorUsuarios;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.GestorRecursos;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.consola.Consola;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorRecursos gestorRecursos = new GestorRecursos();
        Consola consola = new Consola();

        Usuario usuario1 = new Usuario("Marcos", "1", "marcoscassone02@gmail.com");
        gestorUsuarios.agregarUsuario(usuario1);

        // Clase recurso temporal para probar (porque RecursoDigital es abstracta)
        RecursoDigital recurso = new RecursoDigital("Libro de Java", "R001") {
            @Override
            public void mostrarInformacion() {
                System.out.println("Recurso: " + getTitulo() + " | ID: " + getId());
            }
        };

        gestorRecursos.agregarRecurso(recurso);

        consola.mostrarUsuario(usuario1);
        consola.mostrarRecursos(gestorRecursos.getRecursos());
    }
}
