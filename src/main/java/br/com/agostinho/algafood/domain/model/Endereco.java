package br.com.agostinho.algafood.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

    @Column(name = "endereco_cep")
    private String cep;
    @Column(name = "endereco_logradouro")
    private String logradouro;
    @Column(name = "endereco_numero")
    private String numero;
    @Column(name = "endereco_bairro")
    private String bairro;
    @ManyToOne
    @JoinColumn(name = "endereco_cidade")
    private Cidade cidade;
}
