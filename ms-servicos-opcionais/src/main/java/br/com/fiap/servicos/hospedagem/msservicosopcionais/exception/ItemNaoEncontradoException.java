package br.com.fiap.servicos.hospedagem.msservicosopcionais.exception;

public class ItemNaoEncontradoException extends Exception{
    public ItemNaoEncontradoException() {
        super("O item não foi encontrado!!");
    }
}
