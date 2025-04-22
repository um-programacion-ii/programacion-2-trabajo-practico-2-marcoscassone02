package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public class Revista implements RecursoDigital {
    private String titulo;
    private int edicion;

    public Revista(String titulo, int edicion) {
        this.titulo = titulo;
        this.edicion = edicion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return "Revista";
    }

    public Categoria getCategoria() {
        return Categoria.Revista;
    }

    public void mostrarInformacion() {
        System.out.println("📰 Revista: " + titulo + " - Edición: " + edicion);
    }
}


