package br.com.fiap.clientes.mscliente.controller.advice;

import br.com.fiap.clientes.mscliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.clientes.mscliente.exception.NumeroDePassaporteNaoInformadoException;
import br.com.fiap.clientes.mscliente.response.ErrorResponseDetails;
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
    void clienteNaoEncontradoException() {
        ClienteNaoEncontradoException clienteNaoEncontradoException = new ClienteNaoEncontradoException();
        ResponseEntity<?> response = handlerException.clienteNaoEncontradoException(clienteNaoEncontradoException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void numeroDePassaporteNaoInformadoException() {
        NumeroDePassaporteNaoInformadoException numeroDePassaporteNaoInformadoException = new NumeroDePassaporteNaoInformadoException();
        ResponseEntity<?> response = handlerException.numeroDePassaporteNaoInformadoException(numeroDePassaporteNaoInformadoException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
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
        Exception genericException = new Exception("Erro genérico");
        ResponseEntity<?> response = handlerException.genericException(genericException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }
}
