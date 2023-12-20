package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Integer> {
}
