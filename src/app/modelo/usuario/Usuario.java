package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario;

public class Usuario {
    private String nombre;
    private String id;
    private String email;

    public Usuario(String nombre, String id, String email) {
        this.nombre = nombre;
        this.id = id;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getId() { return id; }
    public String getEmail() { return email; }
}
