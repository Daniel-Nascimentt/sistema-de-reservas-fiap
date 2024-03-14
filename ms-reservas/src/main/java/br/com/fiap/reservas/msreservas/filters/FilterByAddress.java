package br.com.fiap.reservas.msreservas.filters;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterByAddress {

    private static final Logger logger = LoggerFactory.getLogger(FilterByAddress.class);

    /**
     * Filtra a lista de quartos removendo aqueles que não atendem ao critério de capacidade de hóspedes.
     *
     * @param quartos Lista de quartos a serem filtrados
     * @param quantidadeHospedes Quantidade de hóspedes solicitada pelo usuário
     * @return Lista de quartos filtrados
     */
    public static List<QuartoResponse> filter(List<QuartoResponse> quartos, int quantidadeHospedes) {
        logger.info("Iniciando filtro de quartos por endereço");

        boolean naoExcedeCapacidadeDeHospedesEmUmQuarto = quartos.stream().anyMatch(q -> q.getTotalHospedes() >= quantidadeHospedes);
        logger.info("Verificando se algum quarto excede a capacidade de hóspedes desejada de {} hospedes...", quantidadeHospedes);
        if(naoExcedeCapacidadeDeHospedesEmUmQuarto){
            logger.info("Capacidade de hóspedes não excede o desejado. Ignorando o filtro de endereço.");
            return quartos;
        }

        Map<String, List<QuartoResponse>> mapa = new HashMap<>();

        logger.info("Iniciando agrupamento de quartos por endereço");

        quartos.forEach(quartoResponse -> {
            mapa.computeIfAbsent(quartoResponse.getEnderecoPropriedade(), k -> new ArrayList<>()).add(quartoResponse);
        });

        logger.info("Quartos agrupados por endereço com sucesso");

        mapa.forEach((endereco, quartosAgrupados) -> {
            int capacidadeTotal = quartosAgrupados.stream()
                    .mapToInt(QuartoResponse::getTotalHospedes)
                    .sum();

            if (capacidadeTotal < quantidadeHospedes) {
                logger.info("Capacidade total dos quartos no endereco {} não atende aos requisitos", endereco);
                quartos.removeAll(quartosAgrupados);
                logger.info("Quartos da propriedade {} removidos com sucesso", endereco);
            }
        });

        logger.info("Filtro de quartos por endereço concluído");
        return quartos;
    }
}

