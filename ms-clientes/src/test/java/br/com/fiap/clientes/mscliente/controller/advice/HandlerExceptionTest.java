package br.com.fiap.clientes.mscliente.controller.advice;

import br.com.fiap.clientes.mscliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.clientes.mscliente.exception.NumeroDePassaporteNaoInformadoException;
import br.com.fiap.clientes.mscliente.response.ErrorResponseDetails;
import br.com.fiap.clientes.mscliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HandlerException.class)
class HandlerExceptionTest {

    @Mock
    private ClienteNaoEncontradoException clienteNaoEncontradoException;

    @Mock
    private NumeroDePassaporteNaoInformadoException numeroDePassaporteNaoInformadoException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @InjectMocks
    private HandlerException handlerException;

    @Test
    void clienteNaoEncontradoException() {
        ResponseEntity<?> response = handlerException.clienteNaoEncontradoException(clienteNaoEncontradoException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorResponseDetails.class, response.getBody().getClass());
    }

    @Test
    void numeroDePassaporteNaoInformadoException() {
        ResponseEntity<?> response = handlerException.numeroDePassaporteNaoInformadoException(numeroDePassaporteNaoInformadoException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
