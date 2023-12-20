package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {
}
