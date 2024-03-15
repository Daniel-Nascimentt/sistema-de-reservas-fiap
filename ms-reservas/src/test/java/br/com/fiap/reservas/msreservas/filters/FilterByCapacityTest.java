package br.com.fiap.reservas.msreservas.filters;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterByCapacityTest {

    @Test
    public void testFilterByCapacity() {

        List<QuartoResponse> quartos = new ArrayList<>();
        quartos.add(new QuartoResponse(1L, 2, "Endereco 1"));
        quartos.add(new QuartoResponse(2L, 3, "Endereco 2"));
        quartos.add(new QuartoResponse(3L, 4, "Endereco 3"));

        int quantidadeHospedes = 3;

        List<QuartoResponse> quartosFiltrados = FilterByCapacity.filter(quartos, quantidadeHospedes);

        assertEquals(2, quartosFiltrados.size());
        assertEquals("Endereco 1", quartosFiltrados.get(0).getEnderecoPropriedade());
        assertEquals("Endereco 2", quartosFiltrados.get(1).getEnderecoPropriedade());
    }

}
