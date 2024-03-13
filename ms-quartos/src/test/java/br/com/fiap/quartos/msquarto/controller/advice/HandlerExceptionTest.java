package br.com.fiap.quartos.msquarto.controller.advice;

import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.QuartoNaoEncontradoException;
import br.com.fiap.quartos.msquarto.response.ErrorResponseDetails;
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
    void testLocalidadeNaoEncontradaException() {
        LocalidadeNaoEncontradaException exception = new LocalidadeNaoEncontradaException();

        ResponseEntity<?> responseEntity = handlerException.localidadeNaoEncontradaException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testPropriedadeNaoEncontradaException() {
        PropriedadeNaoEncontradaException exception = new PropriedadeNaoEncontradaException();

        ResponseEntity<?> responseEntity = handlerException.propriedadeNaoEncontradaException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testQuartoNaoEncontradoException() {
        QuartoNaoEncontradoException exception = new QuartoNaoEncontradoException();

        ResponseEntity<?> responseEntity = handlerException.quartoNaoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testMethodArgumentNotValidException() {

        when(methodArgumentNotValidException.getMessage()).thenReturn("Argumento inválido");
        ResponseEntity<?> response = handlerException.methodArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void testGenericException() {
        Exception exception = new Exception("Erro genérico");

        ResponseEntity<?> responseEntity = handlerException.genericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}
