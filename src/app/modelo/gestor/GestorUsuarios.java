package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor;

import java.util.HashMap;
import java.util.Map;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;

public class GestorUsuarios {
    private Map<String, Usuario> usuarios = new HashMap<>();

    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getEmail(), usuario);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarios.get(email);
    }

    public Map<String, Usuario> obtenerUsuarios() {
        return usuarios;
    }
}

