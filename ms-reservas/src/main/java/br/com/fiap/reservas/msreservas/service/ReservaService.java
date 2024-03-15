package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.client.MsQuartosClient;
import br.com.fiap.reservas.msreservas.client.MsServicosClient;
import br.com.fiap.reservas.msreservas.domain.OpcionaisReserva;
import br.com.fiap.reservas.msreservas.domain.Reserva;
import br.com.fiap.reservas.msreservas.domain.ReservaQuarto;
import br.com.fiap.reservas.msreservas.domain.StatusQuarto;
import br.com.fiap.reservas.msreservas.exception.QuartoJaReservadoException;
import br.com.fiap.reservas.msreservas.exception.ReservaNaoEncontradaException;
import br.com.fiap.reservas.msreservas.filters.FilterByAddress;
import br.com.fiap.reservas.msreservas.filters.FilterByCapacity;
import br.com.fiap.reservas.msreservas.filters.FilterQuarto;
import br.com.fiap.reservas.msreservas.repository.ReservaQuartoRepository;
import br.com.fiap.reservas.msreservas.repository.ReservaRepository;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.response.ItemResponse;
import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import br.com.fiap.reservas.msreservas.response.ReservaResponse;
import br.com.fiap.reservas.msreservas.response.ServicoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    @Autowired
    private MsQuartosClient msQuartosClient;

    @Autowired
    private ReservaQuartoRepository reservaQuartoRepository;

    @Autowired
    private MsServicosClient msServicosClient;

    @Autowired
    private ReservaRepository reservaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    public Page<QuartoResponse> getQuartosDisponiveis(Long idLocalidade, int quantidadeHospedesParaReserva, LocalDate checkin, LocalDate checkout) {
        logger.info("Iniciando execução do método getQuartosDisponiveis...");

        List<QuartoResponse> quartos = obterQuartosPorLocalidade(idLocalidade);
        List<Long> quartosReservados = buscarQuartosReservados(checkin, checkout);
        removerQuartosReservados(quartos, quartosReservados);
        List<QuartoResponse> quartosFiltrados = filtrarQuartos(quartos, quantidadeHospedesParaReserva);

        logger.info("Finalizando execução do método getQuartosDisponiveis...");
        return new PageImpl<>(quartosFiltrados);
    }

    public ReservaResponse preReservar(NovaReservaRequest request) throws QuartoJaReservadoException {
        logger.info("Iniciando pré-reserva solicitada pelo cliente {}...", request.getIdCliente());

        verificarDisponibilidadeDosQuartos(request);

        List<ServicoResponse> servicos = obterServicos(request);
        List<ItemResponse> itens = obterItens(request);
        List<QuartoResponse> quartos = obterQuartos(request);
        Reserva reserva = criarEGravarReserva(request, servicos, itens, quartos);

        logger.info("Pré-reserva concluída com sucesso.");
        return new ReservaResponse(reserva);
    }

    private List<QuartoResponse> obterQuartosPorLocalidade(Long idLocalidade) {
        logger.info("Obtendo quartos disponíveis na localidade com ID: {}", idLocalidade);
        return new ArrayList<>(msQuartosClient.getAllQuartosByLocalidade(idLocalidade).getContent());
    }

    private List<Long> buscarQuartosReservados(LocalDate checkin, LocalDate checkout) {
        logger.info("Buscando ids dos quartos reservados para o período entre {} e {}.", checkin, checkout);
        return reservaQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(checkin, checkout);
    }

    private List<QuartoResponse> removerQuartosReservados(List<QuartoResponse> quartos, List<Long> quartosReservados) {
        logger.info("Removendo quartos reservados da lista de quartos disponíveis...");
        quartos.removeIf(q -> quartosReservados.contains(q.getIdQuarto()));
        return quartos;
    }

    private List<QuartoResponse> filtrarQuartos(List<QuartoResponse> quartos, int quantidadeHospedesParaReserva) {
        logger.info("Filtrando quartos disponíveis com base na quantidade de hóspedes para reserva: {}", quantidadeHospedesParaReserva);
        return FilterQuarto.filtrarQuartos(quartos, quantidadeHospedesParaReserva, FilterByCapacity::filter, FilterByAddress::filter);
    }

    private void verificarDisponibilidadeDosQuartos(NovaReservaRequest request) throws QuartoJaReservadoException {
        logger.info("Verificando se os quartos solicitados estão reservados para o período entre {} e {}.", request.getCheckin(), request.getCheckout());
        List<Long> quartosReservados = buscarQuartosReservados(request.getCheckin(), request.getCheckout());
        if (!Collections.disjoint(quartosReservados, request.getIdsQuarto())) {
            logger.error("Um ou mais quartos solicitados já estão reservados.");
            throw new QuartoJaReservadoException();
        }
    }

    private List<ServicoResponse> obterServicos(NovaReservaRequest request) {
        logger.info("Obtendo serviços...");
        return new ArrayList<>(msServicosClient.obterServicoPorListaIds(request.getIdsServicos(), Pageable.unpaged()).getContent());
    }

    private List<ItemResponse> obterItens(NovaReservaRequest request) {
        logger.info("Obtendo itens...");
        return new ArrayList<>(msServicosClient.obterItensPorListDeIds(request.getIdsItens(), Pageable.unpaged()).getContent());
    }

    private List<QuartoResponse> obterQuartos(NovaReservaRequest request) {
        logger.info("Obtendo quartos...");
        return new ArrayList<>(msQuartosClient.obterQuartosPorListIds(request.getIdsQuarto(), Pageable.unpaged()).getContent());
    }

    private Reserva criarEGravarReserva(NovaReservaRequest request, List<ServicoResponse> servicos, List<ItemResponse> itens, List<QuartoResponse> quartos) {
        logger.info("Criando e salvando reserva com status {}...", StatusQuarto.PEND_CONFIRM_RESERVA);
        Reserva reserva = new Reserva(request.getCheckin(), request.getCheckout(), LocalDateTime.now());
        reservaRepository.save(reserva);

        long diasDeEstadia = ChronoUnit.DAYS.between(request.getCheckin(), request.getCheckout());
        logger.info("Dias de estadia calculados: {} dias.", diasDeEstadia);

        adicionarServicosNaReserva(reserva, servicos);
        adicionarItensNaReserva(reserva, itens);
        adicionarQuartosNaReserva(reserva, quartos, diasDeEstadia);

        logger.info("Salvando reserva atualizada...");
        return reservaRepository.save(reserva);
    }

    private void adicionarServicosNaReserva(Reserva reserva, List<ServicoResponse> servicos) {
        logger.info("Adicionando serviço(s) à reserva...");
        servicos.forEach(s -> {
            reserva.addOpcional(new OpcionaisReserva(s, reserva));
            reserva.somarAoTotalReserva(s.getValorServico());
        });
        logger.info("Serviços adicionados à reserva.");
    }

    private void adicionarItensNaReserva(Reserva reserva, List<ItemResponse> itens) {
        logger.info("Adicionando item(s) à reserva...");
        itens.forEach(i -> {
            reserva.addOpcional(new OpcionaisReserva(i, reserva));
            reserva.somarAoTotalReserva(i.getValorItem());
        });
        logger.info("Itens adicionados à reserva.");
    }

    private void adicionarQuartosNaReserva(Reserva reserva, List<QuartoResponse> quartos, long diasDeEstadia) {
        logger.info("Adicionando quarto(s) à reserva...");
        quartos.forEach(q -> {
            reserva.addQuartoAReserva(new ReservaQuarto(reserva, q.getIdQuarto(), StatusQuarto.PEND_CONFIRM_RESERVA));
            reserva.somarAoTotalReserva(q.getValorDiaria().multiply(new BigDecimal(diasDeEstadia)));
        });
        logger.info("Quartos adicionados à reserva.");
    }

    public ReservaResponse reservar(UUID codigoReserva) throws ReservaNaoEncontradaException {
        Reserva reserva = reservaRepository.findById(codigoReserva).orElseThrow(ReservaNaoEncontradaException::new);
        reserva.confirmarReserva();
        return new ReservaResponse(reservaRepository.save(reserva));
    }
}