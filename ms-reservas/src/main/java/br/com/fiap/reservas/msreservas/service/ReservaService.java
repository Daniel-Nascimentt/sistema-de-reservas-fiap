package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.client.MsClientesClient;
import br.com.fiap.reservas.msreservas.client.MsQuartosClient;
import br.com.fiap.reservas.msreservas.client.MsServicosClient;
import br.com.fiap.reservas.msreservas.domain.OpcionaisReserva;
import br.com.fiap.reservas.msreservas.domain.Reserva;
import br.com.fiap.reservas.msreservas.domain.ReservaQuarto;
import br.com.fiap.reservas.msreservas.domain.StatusQuarto;
import br.com.fiap.reservas.msreservas.exception.*;
import br.com.fiap.reservas.msreservas.filters.FilterByAddress;
import br.com.fiap.reservas.msreservas.filters.FilterByCapacity;
import br.com.fiap.reservas.msreservas.filters.FilterQuarto;
import br.com.fiap.reservas.msreservas.repository.ReservaQuartoRepository;
import br.com.fiap.reservas.msreservas.repository.ReservaRepository;
import br.com.fiap.reservas.msreservas.request.ItemRequest;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.request.ServicoRequest;
import br.com.fiap.reservas.msreservas.response.*;
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
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private MsClientesClient msClientesClient;

    @Autowired
    private EmailService emailService;

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

    public ReservaResponse preReservar(NovaReservaRequest request) throws QuartoJaReservadoException, DataCheckinInvalidaException {
        logger.info("Iniciando pré-reserva solicitada pelo cliente {}...", request.getIdCliente());
        verificarDisponibilidadeDosQuartos(request);
        List<ServicoResponse> servicos = obterServicos(request);
        List<ItemResponse> itens = obterItens(request);
        List<QuartoResponse> quartos = obterQuartos(request);
        ClienteResponse cliente = obterClientePorId(request.getIdCliente());
        Reserva reserva = criarEGravarReserva(request, servicos, itens, quartos, cliente);
        logger.info("Pré-reserva concluída com sucesso.");
        return new ReservaResponse(reserva, getQuartosResponse(reserva.getQuartos()));
    }

    private ClienteResponse obterClientePorId(Long idCliente) {
        logger.info("Obtendo informações do cliente com ID: {}", idCliente);
        return msClientesClient.buscarClientePorId(idCliente);
    }

    private List<QuartoResponse> obterQuartosPorLocalidade(Long idLocalidade) {
        logger.info("Obtendo quartos disponíveis na localidade com ID: {}", idLocalidade);
        return new ArrayList<>(msQuartosClient.getAllQuartosByLocalidade(idLocalidade).getContent());
    }

    private List<Long> buscarQuartosReservados(LocalDate checkin, LocalDate checkout) {
        logger.info("Buscando IDs dos quartos reservados para o período entre {} e {}.", checkin, checkout);
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

    private void verificarDisponibilidadeDosQuartos(NovaReservaRequest request) throws QuartoJaReservadoException, DataCheckinInvalidaException {
        logger.info("Verificando se os quartos solicitados estão reservados para o período entre {} e {}.", request.getCheckin(), request.getCheckout());
        List<Long> quartosReservados = buscarQuartosReservados(request.getCheckin(), request.getCheckout());
        if (!Collections.disjoint(quartosReservados, request.getIdsQuarto())) {
            logger.error("Um ou mais quartos solicitados já estão reservados.");
            throw new QuartoJaReservadoException();
        }
    }

    private List<ServicoResponse> obterServicos(NovaReservaRequest request) {
        logger.info("Iniciando obtenção dos serviços...");

        Map<Long, Long> servicoMap = request.getServicos().stream()
                .collect(Collectors.toMap(ServicoRequest::getIdServico, ServicoRequest::getQuantidade));

        List<Long> idsServicos = new ArrayList<>(servicoMap.keySet());
        List<ServicoResponse> servicos = new ArrayList<>(msServicosClient.obterServicoPorListaIds(idsServicos, Pageable.unpaged()).getContent());

        for (ServicoResponse servico : servicos) {
            Long quantidade = servicoMap.get(servico.getId());
            if (quantidade != null) {
                logger.debug("Atualizando quantidade do serviço com ID {}: {}", servico.getId(), quantidade);
                servico.setQuantidade(quantidade);
            } else {
                logger.warn("Quantidade não encontrada para o serviço com ID: {}", servico.getId());
            }
        }

        logger.info("Serviços obtidos com sucesso.");

        return servicos;
    }

    private List<ItemResponse> obterItens(NovaReservaRequest request) {
        logger.info("Iniciando obtenção dos itens...");

        Map<Long, Long> itemMap = request.getItens().stream()
                .collect(Collectors.toMap(ItemRequest::getIdItem, ItemRequest::getQuantidade));

        List<Long> idsItens = new ArrayList<>(itemMap.keySet());
        List<ItemResponse> itens = new ArrayList<>(msServicosClient.obterItensPorListDeIds(idsItens, Pageable.unpaged()).getContent());

        for (ItemResponse item : itens) {
            Long quantidade = itemMap.get(item.getId());
            if (quantidade != null) {
                logger.debug("Atualizando quantidade do item com ID {}: {}", item.getId(), quantidade);
                item.setQuantidade(quantidade);
            } else {
                logger.warn("Quantidade não encontrada para o item com ID: {}", item.getId());
            }
        }

        logger.info("Itens obtidos com sucesso.");

        return itens;
    }

    private List<QuartoResponse> obterQuartos(NovaReservaRequest request) {
        logger.info("Obtendo quartos...");
        return new ArrayList<>(msQuartosClient.obterQuartosPorListIds(request.getIdsQuarto(), Pageable.unpaged()).getContent());
    }

    private Reserva criarEGravarReserva(NovaReservaRequest request, List<ServicoResponse> servicos, List<ItemResponse> itens, List<QuartoResponse> quartos, ClienteResponse cliente) throws DataCheckinInvalidaException {
        logger.info("Criando e salvando reserva com status {}...", StatusQuarto.PEND_CONFIRM_RESERVA);
        Reserva reserva = new Reserva(request.getCheckin(), request.getCheckout(), LocalDateTime.now(), cliente.getId());
        reservaRepository.save(reserva);
        long diasDeEstadia = ChronoUnit.DAYS.between(request.getCheckin(), request.getCheckout());
        logger.info("Dias de estadia calculados: {} dias.", diasDeEstadia);
        adicionarServicosNaReserva(reserva, servicos, request);
        adicionarItensNaReserva(reserva, itens, request);
        adicionarQuartosNaReserva(reserva, quartos, diasDeEstadia);
        logger.info("Salvando reserva atualizada...");
        return reservaRepository.save(reserva);
    }

    private void adicionarServicosNaReserva(Reserva reserva, List<ServicoResponse> servicos, NovaReservaRequest request) {
        logger.info("Adicionando serviço(s) à reserva...");
        servicos.forEach(s -> {
            // SE CHEGOU AQUI "request.getServos" NAO É NULL
            Long quantidade = new ArrayList<>(request.getServicos()).stream().filter(f -> f.getIdServico().equals(s.getId())).findFirst().orElseThrow().getQuantidade();
            reserva.addOpcional(new OpcionaisReserva(s, reserva, quantidade));
            reserva.somarAoTotalReserva(s.getValorServico().multiply(new BigDecimal(s.getQuantidade())));
        });
        logger.info("Serviços adicionados à reserva.");
    }

    private void adicionarItensNaReserva(Reserva reserva, List<ItemResponse> itens, NovaReservaRequest request) {
        logger.info("Adicionando item(s) à reserva...");
        itens.forEach(i -> {
            // SE CHEGOU AQUI "request.getItens" NAO É NULL
            Long quantidade = new ArrayList<>(request.getItens()).stream().filter(f -> f.getIdItem().equals(i.getId())).findFirst().orElseThrow().getQuantidade();
            reserva.addOpcional(new OpcionaisReserva(i, reserva, quantidade));
            reserva.somarAoTotalReserva(i.getValorItem().multiply(new BigDecimal(i.getQuantidade())));
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

    public ReservaResponse reservar(UUID codigoReserva, Long idCliente) throws ReservaNaoEncontradaException, ClienteInvalidoException {
        logger.info("Iniciando reserva para o código de reserva: {}", codigoReserva);
        Reserva reserva = reservaRepository.findById(codigoReserva)
                .orElseThrow(ReservaNaoEncontradaException::new);
        ClienteResponse cliente = obterClientePorId(idCliente);
        reserva.validarTitularidade(cliente.getId());
        reserva.confirmarReserva();

        ReservaResponse response = new ReservaResponse(reservaRepository.save(reserva), getQuartosResponse(reserva.getQuartos()));
        emailService.enviarEmail(response, cliente.getEmail());
        logger.info("Reserva confirmada com sucesso para o código de reserva: {}", codigoReserva);
        return response;
    }

    private List<QuartoResponse> getQuartosResponse(List<ReservaQuarto> quartos) {
        List<Long> idsDosQuartos = quartos
                .stream()
                .map(ReservaQuarto::getIdQuarto)
                .toList();

        return msQuartosClient.obterQuartosPorListIds(idsDosQuartos, Pageable.unpaged()).getContent();
    }

    public ReservaResponse buscarPorCodigo(UUID codigoReserva, Long idCliente) throws ReservaNaoEncontradaException, ClienteInvalidoException {
        logger.info("Buscando reserva para o código de reserva: {}", codigoReserva);
        Reserva reserva = reservaRepository.findById(codigoReserva)
                .orElseThrow(ReservaNaoEncontradaException::new);
        ClienteResponse cliente = obterClientePorId(idCliente);
        reserva.validarTitularidade(cliente.getId());

        List<Long> idsDosQuartos = reserva.getQuartos()
                .stream()
                .map(ReservaQuarto::getIdQuarto)
                .toList();

        List<QuartoResponse> quartos = msQuartosClient.obterQuartosPorListIds(idsDosQuartos, Pageable.unpaged()).getContent();

        logger.info("Reserva encontrada com sucesso para o código de reserva: {}", codigoReserva);
        return new ReservaResponse(reserva, quartos);
    }

    public ReservaResponse atualizarReserva(NovaReservaRequest request, UUID codigoReserva) throws ReservaNaoEncontradaException, OperacaoReservaNaoPermitidaException, ClienteInvalidoException, DataCheckinInvalidaException {
        logger.info("Iniciando atualização da reserva com o código {}...", codigoReserva);
        Reserva reserva = reservaRepository.findById(codigoReserva)
                .orElseThrow(ReservaNaoEncontradaException::new);
        ClienteResponse cliente = obterClientePorId(request.getIdCliente());
        reserva.validarTitularidade(cliente.getId());
        logger.info("Validando status da reserva...");
        reserva.validarStatusReservado();
        logger.info("Obtendo serviços...");
        List<ServicoResponse> servicos = obterServicos(request);
        logger.info("Obtendo itens...");
        List<ItemResponse> itens = obterItens(request);
        logger.info("Obtendo quartos...");
        List<QuartoResponse> quartos = obterQuartos(request);
        logger.info("Calculando dias de estadia...");
        long diasDeEstadia = ChronoUnit.DAYS.between(request.getCheckin(), request.getCheckout());
        logger.info("Zerando lista de opcionais para atualização...");
        reserva.clearOpcional();
        logger.info("Zerando o valor da diária...");
        reserva.zerarValorDiaria();
        logger.info("Zerando a lista de quartos...");
        reserva.clearQuartos();
        reserva = reservaRepository.save(reserva);
        logger.info("Adicionando serviços à reserva...");
        adicionarServicosNaReserva(reserva, servicos, request);
        logger.info("Adicionando itens à reserva...");
        adicionarItensNaReserva(reserva, itens, request);
        logger.info("Adicionando quartos à reserva...");
        adicionarQuartosNaReserva(reserva, quartos, diasDeEstadia);
        logger.info("Atualizando pré-reserva...");
        reserva.atualizarPreReserva(request);
        logger.info("Salvando reserva atualizada...");
        Reserva reservaAtualizada = reservaRepository.save(reserva);
        logger.info("Reserva atualizada com sucesso.");
        return new ReservaResponse(reservaAtualizada, getQuartosResponse(reserva.getQuartos()));
    }

    public void deletarReserva(UUID codigoReserva) throws ReservaNaoEncontradaException, OperacaoReservaNaoPermitidaException {
        logger.info("Iniciando exclusão da reserva com o código {}...", codigoReserva);
        Reserva reserva = reservaRepository.findById(codigoReserva)
                .orElseThrow(ReservaNaoEncontradaException::new);
        logger.info("Validando status da reserva...");
        reserva.validarStatusReservado();
        logger.info("Zerando o valor da diária...");
        reserva.zerarValorDiaria();
        logger.info("Removendo opcionais da reserva...");
        reserva.clearOpcional();
        logger.info("Removendo quartos da reserva...");
        reserva.clearQuartos();
        logger.info("Excluindo reserva...");
        reservaRepository.delete(reserva);
        logger.info("Reserva excluída com sucesso.");
    }
}