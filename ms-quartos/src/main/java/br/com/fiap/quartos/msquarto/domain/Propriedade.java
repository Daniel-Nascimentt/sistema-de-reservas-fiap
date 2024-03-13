package br.com.fiap.quartos.msquarto.domain;

import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Localidade localidade;

    @ManyToMany(fetch = FetchType.EAGER)
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
    }

    public void atualizar(PropriedadeRequest propriedadeRequest) {
        this.nomePropriedade = propriedadeRequest.getNomePropriedade();
        this.descricaoAmenidades = propriedadeRequest.getDescricaoAmenidades();

    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public void addQuarto(Quarto quarto) {
        this.quartos.add(quarto);
    }

}
