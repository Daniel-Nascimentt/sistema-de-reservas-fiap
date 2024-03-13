package br.com.fiap.quartos.msquarto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "QUARTOS")
@Getter
@NoArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    @NotBlank
    private String descricaoQuarto;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Banheiro banheiro;

    @ManyToMany(mappedBy = "quartos")
    private Set<Propriedade> propriedades = new HashSet<>();

    public void addPropriedadesComQuarto(Propriedade propriedade) {
        this.propriedades.add(propriedade);
    }

}
