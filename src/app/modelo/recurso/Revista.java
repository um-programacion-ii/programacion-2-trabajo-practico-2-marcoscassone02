package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public class Revista implements RecursoDigital {
    private String titulo;
    private int numeroEdicion;

    public Revista(String titulo, int numeroEdicion) {
        this.titulo = titulo;
        this.numeroEdicion = numeroEdicion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return "Revista";
    }

    public void mostrarInformacion() {
        System.out.println("📰 Revista: " + titulo + " - Edición: " + numeroEdicion);
    }
}

