package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public class Libro implements RecursoDigital, Prestable, Renovable {
    private String titulo;
    private String autor;
    private boolean prestado = false;

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

    public Categoria getCategoria() {
        return Categoria.Libro;
    }

    public void mostrarInformacion() {
        System.out.println("📘 Libro: " + titulo + " - Autor: " + autor);
    }

    public void prestar() {
        prestado = true;
    }

    public void devolver() {
        prestado = false;
    }

    public boolean estaPrestado() {
        return prestado;
    }

    public boolean estaDisponible() {
        return !prestado;
    }

    public void renovar() {
        System.out.println("📘 El libro fue renovado.");
    }

    public boolean puedeRenovarse() {
        return true;
    }
}
