package br.com.fiap.reservas.msreservas.exception;

public class ClienteInvalidoException extends Exception {
    public ClienteInvalidoException() {
        super("O cliente da reserva não coincide com o cliente informado na request!!");
    }
}
