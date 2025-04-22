package programacion_2_trabajo_practico_2_marcoscassone02.src.app.consola;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.*;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.*;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.notificacion.ServicioNotificaciones;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Consola {
    private GestorUsuarios gestorUsuarios;
    private GestorRecursos gestorRecursos;
    private ServicioNotificaciones servicioNotificaciones;
    private Scanner scanner = new Scanner(System.in);

    public Consola(GestorUsuarios gestorUsuarios, GestorRecursos gestorRecursos, ServicioNotificaciones servicioNotificaciones) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorRecursos = gestorRecursos;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    public void iniciar() {
        System.out.println("Bienvenido al Sistema de Gestión de Biblioteca\n");

        gestorUsuarios.agregarUsuario(new Usuario("Marcos", 1, "marcos02@gmail.com"));

        gestorRecursos.agregarRecurso(new Libro("El Principito", "Antoine de Saint-Exupéry"));
        gestorRecursos.agregarRecurso(new Revista("Science Today", 108));
        gestorRecursos.agregarRecurso(new Audiolibro("1984", "Pepe Voice"));

        mostrarMenu();
    }

    private void mostrarMenu() {
        System.out.println("Menú de Gestión de Recursos");
        System.out.println("1. Mostrar todos los recursos");
        System.out.println("2. Buscar recurso por título");
        System.out.println("3. Buscar y filtrar recursos");
        System.out.println("4. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> mostrarRecursos();
            case 2 -> buscarRecursoPorTitulo();
            case 3 -> buscarYFiltrar();
            default -> System.out.println("Hasta luego!");
        }
    }

    private void mostrarRecursos() {
        List<RecursoDigital> recursos = gestorRecursos.obtenerRecursos();
        for (RecursoDigital recurso : recursos) {
            recurso.mostrarInformacion();
            recurso.visualizarEnConsola();
        }
    }

    private void buscarRecursoPorTitulo() {
        System.out.print("Ingrese título a buscar: ");
        String titulo = scanner.nextLine();
        List<RecursoDigital> resultados = gestorRecursos.buscarPorTitulo(titulo);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron recursos con ese título.");
        } else {
            System.out.println("Recursos encontrados:");
            for (RecursoDigital recurso : resultados) {
                recurso.mostrarInformacion();
            }
        }
    }
    private void buscarYFiltrar() {
    System.out.print("Ingrese texto del título (deje vacío para omitir): ");
    String titulo = scanner.nextLine();

    System.out.println("Seleccione categoría (Libro, Revista, Audiolibro o vacía): ");
    String categoriaInput = scanner.nextLine();

    List<RecursoDigital> resultados = gestorRecursos.obtenerRecursos();

    if (!titulo.isEmpty()) {
        resultados = gestorRecursos.buscarPorTitulo(titulo);
    }

    if (!categoriaInput.isEmpty()) {
        try {
            Categoria categoria = Categoria.valueOf(categoriaInput);
            resultados = resultados.stream()
                    .filter(r -> r.getCategoria() == categoria)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría inválida. Se omite filtro de categoría.");
        }
    }

    resultados = resultados.stream()
            .sorted(Comparator.comparing(RecursoDigital::getTitulo))
            .collect(Collectors.toList());

    if (resultados.isEmpty()) {
        System.out.println("No se encontraron recursos.");
    } else {
        System.out.println("Resultados encontrados:");
        for (RecursoDigital recurso : resultados) {
            recurso.mostrarInformacion();
        }
    }
}


}

