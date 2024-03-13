package br.com.fiap.quartos.msquarto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOCALIDADES")
@Getter
@NoArgsConstructor
public class Localidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome_localidade")
    private String nomeLocalidade;

    @OneToMany(mappedBy = "localidade")
    private List<Propriedade> propriedades = new ArrayList<>();

}
