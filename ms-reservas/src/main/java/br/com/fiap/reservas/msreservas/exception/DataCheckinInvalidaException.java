package br.com.fiap.reservas.msreservas.exception;

public class DataCheckinInvalidaException extends Exception {
    public DataCheckinInvalidaException() {
        super("A data de checkin deve ser menor do que a data de checkout!!");
    }
}
