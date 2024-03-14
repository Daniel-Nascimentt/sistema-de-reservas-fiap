package br.com.fiap.reservas.msreservas.filters;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FilterQuarto {

    private static final Logger logger = LoggerFactory.getLogger(FilterQuarto.class);

    public static List<QuartoResponse> filtrarQuartos(List<QuartoResponse> quartos, int quantidadeHospedes, FilterFunction... filters) {
        logger.info("Iniciando filtragem dos quartos...");
        for (FilterFunction filter : filters) {
            logger.info("Aplicando filtro: {}", filter.getClass().getSimpleName());
            quartos = filter.apply(quartos, quantidadeHospedes);
        }

        logger.info("Filtragem dos quartos concluída");
        return quartos;
    }

}