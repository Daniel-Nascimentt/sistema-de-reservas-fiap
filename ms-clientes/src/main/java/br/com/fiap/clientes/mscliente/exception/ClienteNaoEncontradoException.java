package br.com.fiap.clientes.mscliente.exception;

public class ClienteNaoEncontradoException extends Exception{
    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado!!");
    }
}
