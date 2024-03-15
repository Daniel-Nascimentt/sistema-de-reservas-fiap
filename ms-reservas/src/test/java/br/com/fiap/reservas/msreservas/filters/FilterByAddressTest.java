package br.com.fiap.reservas.msreservas.filters;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterByAddressTest {

    private static final Logger logger = LoggerFactory.getLogger(FilterByAddressTest.class);

    @Test
    void testFilter() {
        logger.info("Iniciando teste de filtragem de quartos por endereço...");

        List<QuartoResponse> quartos = new ArrayList<>();
        quartos.add(new QuartoResponse(1L, 1, "Endereco 1"));
        quartos.add(new QuartoResponse(2L, 2, "Endereco 1"));
        quartos.add(new QuartoResponse(3L, 2, "Endereco 2"));

        int quantidadeHospedes = 3;

        List<QuartoResponse> quartosFiltrados = FilterByAddress.filter(quartos, quantidadeHospedes);

        assertEquals(2, quartosFiltrados.size());
        assertEquals("Endereco 1", quartosFiltrados.get(0).getEnderecoPropriedade());
        assertEquals("Endereco 1", quartosFiltrados.get(1).getEnderecoPropriedade());

        logger.info("Teste de filtragem de quartos por endereço concluído.");
    }
}