package programacion_2_trabajo_practico_2_marcoscassone02.src.app;  

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.GestorUsuarios;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.GestorRecursos;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.consola.Consola;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorRecursos gestorRecursos = new GestorRecursos();
        Consola consola = new Consola(gestorUsuarios, gestorRecursos);
        consola.iniciar();
    }
}