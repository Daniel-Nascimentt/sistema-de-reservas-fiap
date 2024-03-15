package br.com.fiap.reservas.msreservas.exception;

public class DatacheckinInvalida extends Exception {
    public DatacheckinInvalida() {
        super("A data de checkin deve ser menor do que a data de checkout!!");
    }
}
