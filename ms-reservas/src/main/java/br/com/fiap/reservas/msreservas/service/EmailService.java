package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.client.MsEmailClient;
import br.com.fiap.reservas.msreservas.client.MsServicosClient;
import br.com.fiap.reservas.msreservas.request.EmailRequest;
import br.com.fiap.reservas.msreservas.response.ItemResponse;
import br.com.fiap.reservas.msreservas.response.OpcionaisReservaResponse;
import br.com.fiap.reservas.msreservas.response.ReservaResponse;
import br.com.fiap.reservas.msreservas.response.ServicoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private MsServicosClient servicosClient;

    @Autowired
    private MsEmailClient msEmailClient;

    @Async
    public void enviarEmail(ReservaResponse response, String emailCliente){
        msEmailClient.enviarEmail(new EmailRequest(response.getCodigoReserva().toString(), "RESERVA CONFIRMADA COM SUCESSO - FIAP", emailCliente, emailBody(response)));
    }

    private String emailBody(ReservaResponse reserva) {
        StringBuilder emailContent = new StringBuilder();
        appendConfirmationHeader(emailContent, reserva);
        appendReservationDetails(emailContent, reserva);
        appendOptionalItems(emailContent, reserva.getServicosOpcionais());
        appendClosingMessage(emailContent);
        return emailContent.toString();
    }

    private void appendConfirmationHeader(StringBuilder emailContent, ReservaResponse reserva) {
        emailContent.append("CONFIRMAÇÃO DE RESERVA \n\n");
    }

    private void appendReservationDetails(StringBuilder emailContent, ReservaResponse reserva) {
        emailContent.append("Código de reserva: ").append(reserva.getCodigoReserva()).append("\n")
                .append("Check-in: ").append(reserva.getCheckin()).append("\n")
                .append("Check-out: ").append(reserva.getCheckout()).append("\n")
                .append("Valor Total da Reserva: ").append(reserva.getValorTotalReserva()).append("\n\n");
    }

    private void appendOptionalItems(StringBuilder emailContent, List<OpcionaisReservaResponse> servicosOpcionais) {
        List<Long> idsServicos = new ArrayList<>();
        List<Long> idsItens = new ArrayList<>();

        for (OpcionaisReservaResponse opcional : servicosOpcionais) {
            String tipo = opcional.getIdOpcional().substring(0, 2);
            Long idSemPrefixo = Long.parseLong(opcional.getIdOpcional().substring(2));

            if (tipo.equals("S_")) {
                idsServicos.add(idSemPrefixo);
            } else if (tipo.equals("I_")) {
                idsItens.add(idSemPrefixo);
            } else {
                logger.error("ID inválido: {}", opcional.getIdOpcional());
            }
        }

        List<ItemResponse> itens = new ArrayList<>(servicosClient.obterItensPorListDeIds(idsItens, Pageable.unpaged()).getContent());
        List<ServicoResponse> servicos = new ArrayList<>(servicosClient.obterServicoPorListaIds(idsServicos, Pageable.unpaged()).getContent());

        appendOptionalItemsDetails(emailContent, itens, servicos, servicosOpcionais);
    }

    private void appendOptionalItemsDetails(StringBuilder emailContent, List<ItemResponse> itens, List<ServicoResponse> servicos, List<OpcionaisReservaResponse> servicosOpcionais) {
        emailContent.append("Itens opcionais adicionados: \n");

        for (ItemResponse item : itens) {
            emailContent.append("ITEM: ").append(item.getNomeItem()).append("\n")
                    .append("VALOR: ").append(item.getValorItem()).append("\n");
            Long quantidade = getQuantidadeForItem(servicosOpcionais, item.getId());
            emailContent.append("QUANTIDADE: ").append(quantidade).append("\n");
        }

        for (ServicoResponse servico : servicos) {
            emailContent.append("SERVIÇO: ").append(servico.getNomeServico()).append("\n")
                    .append("VALOR: ").append(servico.getValorServico()).append("\n");
            Long quantidade = getQuantidadeForServico(servicosOpcionais, servico.getId());
            emailContent.append("QUANTIDADE: ").append(quantidade).append("\n");
        }
    }

    private Long getQuantidadeForItem(List<OpcionaisReservaResponse> servicosOpcionais, Long itemId) {
        return servicosOpcionais.stream()
                .filter(opcional -> opcional.getIdOpcional().contains("I_") && Long.parseLong(opcional.getIdOpcional().substring(2)) == itemId)
                .findFirst()
                .map(OpcionaisReservaResponse::getQuantidade)
                .orElse(0L);
    }

    private Long getQuantidadeForServico(List<OpcionaisReservaResponse> servicosOpcionais, Long servicoId) {
        return servicosOpcionais.stream()
                .filter(opcional -> opcional.getIdOpcional().contains("S_") && Long.parseLong(opcional.getIdOpcional().substring(2)) == servicoId)
                .findFirst()
                .map(OpcionaisReservaResponse::getQuantidade)
                .orElse(0L);
    }

    private void appendClosingMessage(StringBuilder emailContent) {
        emailContent.append("\nAtenciosamente: Equipe FIAP de RESERVAS.");
    }
}
