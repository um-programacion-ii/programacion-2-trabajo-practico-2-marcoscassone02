package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

import java.time.LocalDate;

public class Prestamo {
    private RecursoDigital recurso;
    private int idUsuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(RecursoDigital recurso, int idUsuario) {
        this.recurso = recurso;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = null;
    }

    public void marcarDevuelto() {
        this.fechaDevolucion = LocalDate.now();
    }

    public boolean estaDevuelto() {
        return fechaDevolucion != null;
    }

    public RecursoDigital getRecurso() {
        return recurso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
}

