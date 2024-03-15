package br.com.fiap.reservas.msreservas.exception;

public class OperacaoReservaNaoPermitidaException extends Exception {
    public OperacaoReservaNaoPermitidaException() {
        super("Não é possivel realizar essa operação em reservas já confirmadas!!");
    }
}
