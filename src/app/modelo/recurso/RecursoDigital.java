package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public interface RecursoDigital {
    String getTitulo();
    String getTipo();
    void mostrarInformacion();
    default void visualizarEnConsola() {
        System.out.println("Visualizando recurso: " + getTitulo() + " (" + getTipo() + ")");
    }
}


