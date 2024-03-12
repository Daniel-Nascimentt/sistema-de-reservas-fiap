package br.com.fiap.servicos.hospedagem.msservicosopcionais.controller.advice;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ItemNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ServicoNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ErrorResponseDetails;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(HandlerException.class)
class HandlerExceptionTest {

    @Mock
    private ItemNaoEncontradoException itemNaoEncontradoException;

    @Mock
    private ServicoNaoEncontradoException servicoNaoEncontradoException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @InjectMocks
    private HandlerException handlerException;

    @Test
    void itemNaoEncontradoException() {
        ResponseEntity<?> response = handlerException.itemNaoEncontradoException(itemNaoEncontradoException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void servicoNaoEncontradoException() {
        ResponseEntity<?> response = handlerException.servicoNaoEncontradoException(servicoNaoEncontradoException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void methodArgumentNotValidException() {
        ResponseEntity<?> response = handlerException.methodArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void genericException() {
        Exception genericException = new Exception("Erro genérico");
        ResponseEntity<?> response = handlerException.genericException(genericException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }
}
