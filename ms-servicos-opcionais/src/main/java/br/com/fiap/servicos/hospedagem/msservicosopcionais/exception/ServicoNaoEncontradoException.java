package br.com.fiap.servicos.hospedagem.msservicosopcionais.exception;

public class ServicoNaoEncontradoException extends Exception{
    public ServicoNaoEncontradoException() {
        super("O serviço não foi encontrado!!");
    }
}
