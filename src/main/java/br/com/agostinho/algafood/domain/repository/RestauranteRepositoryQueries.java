package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
    List<Restaurante> findByCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
}
