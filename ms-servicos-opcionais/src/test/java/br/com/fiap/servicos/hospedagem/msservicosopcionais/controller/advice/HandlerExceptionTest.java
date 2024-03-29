package br.com.fiap.servicos.hospedagem.msservicosopcionais.controller.advice;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ItemNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ServicoNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ErrorResponseDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HandlerExceptionTest {

    @InjectMocks
    private HandlerException handlerException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Test
    void itemNaoEncontradoException() {
        ItemNaoEncontradoException exception = new ItemNaoEncontradoException();

        ResponseEntity<?> responseEntity = handlerException.itemNaoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void servicoNaoEncontradoException() {
        ServicoNaoEncontradoException exception = new ServicoNaoEncontradoException();

        ResponseEntity<?> responseEntity = handlerException.servicoNaoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void methodArgumentNotValidException() {
        when(methodArgumentNotValidException.getMessage()).thenReturn("Argumento inválido");
        ResponseEntity<?> response = handlerException.methodArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void genericException() {
        Exception exception = new Exception("Erro genérico");

        ResponseEntity<?> responseEntity = handlerException.genericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}