package programacion_2_trabajo_practico_2_marcoscassone02.src.app.consola;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.RecursoDigital;
import java.util.List;

public class Consola {

    public void mostrarUsuario(Usuario usuario) {
        System.out.println("Usuario:");
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("ID: " + usuario.getId());
        System.out.println("Email: " + usuario.getEmail());
    }

    public void mostrarRecursos(List<RecursoDigital> recursos) {
        for (RecursoDigital recurso : recursos) {
            recurso.mostrarInformacion();
        }
    }
}

