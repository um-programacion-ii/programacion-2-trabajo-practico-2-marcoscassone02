package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.Categoria;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.Prestable;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.RecursoDigital;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.excepcion.RecursoNoDisponibleException;

public class GestorRecursos {
    private List<RecursoDigital> recursos = new ArrayList<>();

    public void agregarRecurso(RecursoDigital recurso) {
        recursos.add(recurso);
    }

    public List<RecursoDigital> obtenerRecursos() {
        return recursos;
    }

    public List<RecursoDigital> buscarPorTitulo(String titulo) {
        return recursos.stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<RecursoDigital> filtrarPorCategoria(Categoria categoria) {
        return recursos.stream()
                .filter(r -> r.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<RecursoDigital> ordenarPorTitulo() {
        return recursos.stream()
                .sorted(Comparator.comparing(RecursoDigital::getTitulo))
                .collect(Collectors.toList());
    }
    
    public void prestarRecurso(RecursoDigital recurso) throws RecursoNoDisponibleException {
    if (recurso instanceof Prestable prestable) {
        if (prestable.estaPrestado()) {
            throw new RecursoNoDisponibleException("El recurso ya está prestado.");
        } else {
            prestable.prestar();
        }
    } else {
        throw new RecursoNoDisponibleException("Este recurso no se puede prestar.");
    }
}
}