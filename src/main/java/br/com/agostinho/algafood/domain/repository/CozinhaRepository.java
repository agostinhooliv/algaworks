package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CozinhaRepository extends JpaRepository<Cozinha, Integer> {
}
