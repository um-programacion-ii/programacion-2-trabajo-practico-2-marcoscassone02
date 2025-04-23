package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario;

public class Usuario {
    private String nombre;
    private int id;
    private String email;

    public Usuario(String nombre, int id, String email) {
        this.nombre = nombre;
        this.id = id;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
