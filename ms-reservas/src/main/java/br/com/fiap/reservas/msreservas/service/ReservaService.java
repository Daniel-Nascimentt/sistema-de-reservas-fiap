package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.filters.FilterByAddress;
import br.com.fiap.reservas.msreservas.filters.FilterByCapacity;
import br.com.fiap.reservas.msreservas.filters.FilterQuarto;
import br.com.fiap.reservas.msreservas.repository.DisponibilidadeQuartoRepository;
import br.com.fiap.reservas.msreservas.client.MsQuartosClient;
import br.com.fiap.reservas.msreservas.request.DisponibilidadeRequest;
import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private MsQuartosClient msQuartosClient;

    @Autowired
    private DisponibilidadeQuartoRepository disponibilidadeQuartoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    public Page<QuartoResponse> getQuartosDisponiveis(Long idLocalidade, int quantidadeHospedesParaReserva, LocalDate checkin, LocalDate checkout) {
        logger.info("Iniciando execução do método getQuartosDisponiveis...");

        logger.info("Obtendo quartos disponíveis na localidade com ID: {}", idLocalidade);
        List<QuartoResponse> quartos = new ArrayList<>(msQuartosClient.getAllQuartosByLocalidade(idLocalidade).getContent());
        logger.info("Lista de quartos obtida com sucesso.");

        logger.info("Buscando ids dos quartos reservados para o período entre {} e {}.", checkin, checkout);
        List<Long> quartosReservados = disponibilidadeQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(checkin, checkout);
        logger.info("Quartos já reservados encontrados com sucesso.");

        logger.info("Removendo quartos reservados da lista de quartos disponíveis...");
        quartos.removeIf(q -> quartosReservados.contains(q.getIdQuarto()));
        logger.info("Quartos já reservados removidos com sucesso.");

        logger.info("Filtrando quartos disponíveis com base na quantidade de hóspedes para reserva: {}", quantidadeHospedesParaReserva);
        List<QuartoResponse> quartosFiltrados = FilterQuarto.filtrarQuartos(quartos, quantidadeHospedesParaReserva, FilterByCapacity::filter, FilterByAddress::filter);
        logger.info("Quartos filtrados com sucesso.");

        logger.info("Finalizando execução do método getQuartosDisponiveis...");
        return new PageImpl<>(quartosFiltrados);
    }

}
