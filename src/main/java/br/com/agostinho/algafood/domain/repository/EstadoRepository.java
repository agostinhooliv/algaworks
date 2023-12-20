package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
