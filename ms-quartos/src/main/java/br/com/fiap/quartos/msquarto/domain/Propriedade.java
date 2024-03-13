package br.com.fiap.quartos.msquarto.domain;

import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROPRIEDADES")
@Getter
@NoArgsConstructor
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nomePropriedade;

    @NotBlank
    private String descricaoAmenidades;

    @NotBlank
    private String enderecoPropriedade;

    @ManyToOne(fetch = FetchType.LAZY)
    private Localidade localidade;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "propriedades_quartos",
            joinColumns = @JoinColumn(name = "propriedade_id"),
            inverseJoinColumns = @JoinColumn(name = "quarto_id")
    )
    private Set<Quarto> quartos = new HashSet<>();

    public Propriedade(PropriedadeRequest propriedadeRequest, Localidade localidade) {
        this.nomePropriedade = propriedadeRequest.getNomePropriedade();
        this.descricaoAmenidades = propriedadeRequest.getDescricaoAmenidades();
        this.localidade = localidade;
        this.enderecoPropriedade = propriedadeRequest.getEnderecoPropriedade();
    }

    public void atualizar(PropriedadeRequest propriedadeRequest) {
        this.nomePropriedade = propriedadeRequest.getNomePropriedade();
        this.descricaoAmenidades = propriedadeRequest.getDescricaoAmenidades();
        this.enderecoPropriedade = propriedadeRequest.getEnderecoPropriedade();

    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public void addQuarto(Quarto quarto) {
        this.quartos.add(quarto);
    }

    public void removerQuarto(Quarto quarto) {
        this.quartos.remove(quarto);
    }

    public void removerTodosQuartos() {
        this.getQuartos().removeAll(this.quartos);
    }
}
