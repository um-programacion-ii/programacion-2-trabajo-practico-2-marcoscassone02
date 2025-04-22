package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public class Audiolibro implements RecursoDigital, Prestable {
    private String titulo;
    private String narrador;
    private boolean prestado = false;

    public Audiolibro(String titulo, String narrador) {
        this.titulo = titulo;
        this.narrador = narrador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return "Audiolibro";
    }

    public Categoria getCategoria() {
        return Categoria.Audiolibro;
    }

    public void mostrarInformacion() {
        System.out.println("🎧 Audiolibro: " + titulo + " - Narrador: " + narrador);
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
}
