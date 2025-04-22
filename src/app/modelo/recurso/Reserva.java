package programacion_2_trabajo_practico_2_marcoscassone02.src.app.modelo.recurso;

import java.time.LocalDate;

public class Reserva implements Comparable<Reserva> {
    private int idUsuario;
    private LocalDate fechaReserva;

    public Reserva(int idUsuario) {
        this.idUsuario = idUsuario;
        this.fechaReserva = LocalDate.now();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    @Override
    public int compareTo(Reserva otra) {
        return this.fechaReserva.compareTo(otra.fechaReserva);
    }
}
