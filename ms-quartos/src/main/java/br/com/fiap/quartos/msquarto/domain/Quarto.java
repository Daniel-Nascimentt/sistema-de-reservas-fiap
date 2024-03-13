package br.com.fiap.quartos.msquarto.domain;

import br.com.fiap.quartos.msquarto.request.QuartoRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "QUARTOS")
@Getter
@NoArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    @NotBlank
    private String descricaoQuarto;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Banheiro banheiro;

    @ManyToMany(mappedBy = "quartos", cascade = CascadeType.PERSIST)
    private Set<Propriedade> propriedades = new HashSet<>();

    @NotNull
    private int totalHospedes;

    @NotNull
    private BigDecimal valorDiaria;

    public Quarto(TipoQuarto tipoQuarto, String descricaoQuarto, Banheiro banheiro, int totalHospedes, BigDecimal valorDiaria) {
        this.tipoQuarto = tipoQuarto;
        this.descricaoQuarto = descricaoQuarto;
        this.banheiro = banheiro;
        this.totalHospedes = totalHospedes;
        this.valorDiaria = valorDiaria;
    }

    public void associarAPropriedade(Propriedade propriedade) {
        this.propriedades.add(propriedade);
    }

    public void atualizar(QuartoRequest quartoRequest, Propriedade propriedade) {
        this.tipoQuarto = quartoRequest.getTipoQuarto();
        this.descricaoQuarto = quartoRequest.getDescricaoQuarto();
        this.banheiro = quartoRequest.getBanheiro().toDomain();
        this.propriedades.removeIf(prop -> prop.getId().equals(propriedade.getId()));
        this.associarAPropriedade(propriedade);
    }
}
