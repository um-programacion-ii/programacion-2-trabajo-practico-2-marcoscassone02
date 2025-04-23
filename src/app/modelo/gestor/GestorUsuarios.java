package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.excepcion.UsuarioNoEncontradoException;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;

public class GestorUsuarios {
    private Map<String, Usuario> usuariosPorEmail = new HashMap<>();
    private List<Usuario> usuarios = new ArrayList<>();

    public void agregarUsuario(Usuario usuario) {
        usuariosPorEmail.put(usuario.getEmail(), usuario);
        usuarios.add(usuario);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuariosPorEmail.get(email);
    }

    public Map<String, Usuario> obtenerUsuariosPorEmail() {
        return usuariosPorEmail;
    }

    public Usuario obtenerUsuarioPorId(int id) throws UsuarioNoEncontradoException {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado."));
    }
}


