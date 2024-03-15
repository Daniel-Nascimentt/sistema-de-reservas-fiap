package br.com.fiap.reservas.msreservas.controller.advice;

import br.com.fiap.reservas.msreservas.controller.advice.HandlerException;
import br.com.fiap.reservas.msreservas.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class HandlerExceptionTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Test
    public void testClienteInvalidoException() {
        HandlerException handler = new HandlerException();
        ClienteInvalidoException ex = new ClienteInvalidoException();

        ResponseEntity<?> response = handler.clienteInvalidoException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDataCheckinInvalidaException() {
        HandlerException handler = new HandlerException();
        DataCheckinInvalidaException ex = new DataCheckinInvalidaException();

        ResponseEntity<?> response = handler.dataCheckinInvalidaException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testOperacaoReservaNaoPermitidaException() {
        HandlerException handler = new HandlerException();
        OperacaoReservaNaoPermitidaException ex = new OperacaoReservaNaoPermitidaException();

        ResponseEntity<?> response = handler.operacaoReservaNaoPermitidaException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testQuartoJaReservadoException() {
        HandlerException handler = new HandlerException();
        QuartoJaReservadoException ex = new QuartoJaReservadoException();

        ResponseEntity<?> response = handler.quartoJaReservadoException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testReservaNaoEncontradaException() {
        HandlerException handler = new HandlerException();
        ReservaNaoEncontradaException ex = new ReservaNaoEncontradaException();

        ResponseEntity<?> response = handler.reservaNaoEncontradaException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testMethodArgumentNotValidException() {
        HandlerException handler = new HandlerException();
        when(methodArgumentNotValidException.getMessage()).thenReturn("Argumento inválido");
        ResponseEntity<?> response = handler.methodArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGenericException() {
        HandlerException handler = new HandlerException();
        Exception ex = new Exception("Erro genérico");

        ResponseEntity<?> response = handler.genericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
