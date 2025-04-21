package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

public abstract class RecursoDigital {
    protected String titulo;
    protected String id;

    public RecursoDigital(String titulo, String id) {
        this.titulo = titulo;
        this.id = id;
    }

    public String getTitulo() { return titulo; }
    public String getId() { return id; }

    public abstract void mostrarInformacion();
}

