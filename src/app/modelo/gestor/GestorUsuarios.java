package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor;

import java.util.ArrayList;
import java.util.List;


import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;

public class GestorUsuarios {
    private List<Usuario> usuarios = new ArrayList<>();

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarios;
    }
}

