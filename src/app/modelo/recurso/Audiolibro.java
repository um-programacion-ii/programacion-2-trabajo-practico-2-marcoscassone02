package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public class Audiolibro implements RecursoDigital {
    private String titulo;
    private String narrador;

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

    public void mostrarInformacion() {
        System.out.println("🎧 Audiolibro: " + titulo + " - Narrador: " + narrador);
    }
}

