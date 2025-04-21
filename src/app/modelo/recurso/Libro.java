package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public class Libro implements RecursoDigital {
    private String titulo;
    private String autor;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return "Libro";
    }

    public void mostrarInformacion() {
        System.out.println("📘 Libro: " + titulo + " - Autor: " + autor);
    }
}

