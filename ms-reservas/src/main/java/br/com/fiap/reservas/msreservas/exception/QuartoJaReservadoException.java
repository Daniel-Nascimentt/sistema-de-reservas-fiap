package br.com.fiap.reservas.msreservas.exception;

public class QuartoJaReservadoException extends Exception {
    public QuartoJaReservadoException() {
        super("Esse quarto já foi reservado!!");
    }
}
