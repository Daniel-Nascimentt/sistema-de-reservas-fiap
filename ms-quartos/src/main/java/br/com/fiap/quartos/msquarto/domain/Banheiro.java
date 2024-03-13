package br.com.fiap.quartos.msquarto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "BANHEIROS")
@Getter
@NoArgsConstructor
public class Banheiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoBanheiro tipoBanheiro;

    @NotBlank
    private String descricaoBanheiro;

    public Banheiro(TipoBanheiro tipoBanheiro, String descricaoBanheiro) {
        this.tipoBanheiro = tipoBanheiro;
        this.descricaoBanheiro = descricaoBanheiro;
    }
}
