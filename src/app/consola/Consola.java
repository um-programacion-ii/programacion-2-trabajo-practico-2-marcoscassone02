package programacion_2_trabajo_practico_2_marcoscassone02.src.app.consola;

import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.usuario.Usuario;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.gestor.*;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.*;
import programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso.excepcion.RecursoNoDisponibleException;
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
        System.out.println("4. Prestar recurso");
        System.out.println("5. Devolver recurso");
        System.out.println("6. Reservar recurso");
        System.out.println("7. Reporte de recursos más prestados");
        System.out.println("8. Reporte de usuarios más activos");
        System.out.println("9. Estadísticas por categoría");
        System.out.println("10. Salir");
    
        int opcion = scanner.nextInt();
        scanner.nextLine();
    
        switch (opcion) {
            case 1 -> mostrarRecursos();
            case 2 -> buscarRecursoPorTitulo();
            case 3 -> buscarYFiltrar();
            case 4 -> prestarRecurso();
            case 5 -> devolverRecurso();
            case 6 -> reservarRecurso();
            case 7 -> gestorRecursos.mostrarReportePrestamos();
            case 8 -> gestorRecursos.mostrarReporteUsuariosActivos();
            case 9 -> gestorRecursos.mostrarEstadisticasPorCategoria();
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
    
    System.out.println("Seleccione categoría (Libro, Revista, Audiolibro o vacía): ");
    String categoriaInput = scanner.nextLine();

    List<RecursoDigital> resultados = gestorRecursos.obtenerRecursos();
    
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
    private void prestarRecurso() {
        System.out.print("Ingrese título del recurso a prestar: ");
        String titulo = scanner.nextLine();
        List<RecursoDigital> recursos = gestorRecursos.buscarPorTitulo(titulo);

        if (recursos.isEmpty()) {
            System.out.println("No se encontró el recurso.");
            return;
        }

        System.out.print("Ingrese ID del usuario: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        try {
            gestorRecursos.prestarRecurso(recursos.get(0), idUsuario);
        } catch (RecursoNoDisponibleException e) {
            System.out.println(e.getMessage());
        }
    }

    private void devolverRecurso() {
        System.out.print("Ingrese título del recurso a devolver: ");
        String titulo = scanner.nextLine();
        List<RecursoDigital> recursos = gestorRecursos.buscarPorTitulo(titulo);
    
        if (recursos.isEmpty()) {
            System.out.println("No se encontró el recurso.");
            return;
        }
    
        gestorRecursos.devolverRecurso(recursos.get(0));
    }

private void reservarRecurso() {
    System.out.print("Ingrese título del recurso a reservar: ");
    String titulo = scanner.nextLine();
    List<RecursoDigital> recursos = gestorRecursos.buscarPorTitulo(titulo);

    if (recursos.isEmpty()) {
        System.out.println("No se encontró el recurso.");
        return;
    }

    System.out.print("Ingrese ID del usuario: ");
    int idUsuario = scanner.nextInt();
    scanner.nextLine();

    gestorRecursos.reservarRecurso(recursos.get(0), idUsuario);
    System.out.println("Recurso reservado correctamente.");

    gestorRecursos.procesarReservas(recursos.get(0));
}

private void verReservas() {
    System.out.print("Ingrese título del recurso: ");
    String titulo = scanner.nextLine();
    List<RecursoDigital> recursos = gestorRecursos.buscarPorTitulo(titulo);

    if (recursos.isEmpty()) {
        System.out.println("No se encontró el recurso.");
        return;
    }

    gestorRecursos.mostrarReservas(recursos.get(0));
}

}