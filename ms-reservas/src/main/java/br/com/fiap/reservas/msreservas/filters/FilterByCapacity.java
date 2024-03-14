package br.com.fiap.reservas.msreservas.filters;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByCapacity {

    private static final Logger logger = LoggerFactory.getLogger(FilterByCapacity.class);

    /**
     * Filtra uma lista de quartos com base na capacidade de hóspedes, permitindo uma margem de uma pessoa a mais e uma a menos.
     *
     * @param quartos Lista de quartos a serem filtrados
     * @param quantidadeHospedes Quantidade de hóspedes desejada
     * @return Lista de quartos filtrados
     */
    public static List<QuartoResponse> filter(List<QuartoResponse> quartos, int quantidadeHospedes) {
        logger.info("Iniciando filtro de quartos por capacidade");

        List<QuartoResponse> quartosFiltrados = quartos.stream()
                .filter(q -> q.getTotalHospedes() <= quantidadeHospedes)
                .collect(Collectors.toList());

        logger.info("Filtro de quartos por capacidade concluído");
        return quartosFiltrados;
    }

}
