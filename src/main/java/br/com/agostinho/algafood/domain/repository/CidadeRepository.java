package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    List<Cidade> findByNomeContaining(String nome);

    List<Cidade> findByNomeContainingAndEstadoId(String nome, Integer estadoId);

    Optional<Cidade> findFirstCidadeByNomeContaining(String nome);

    List<Cidade> findTop2ByNomeContaining(String nome);

    Integer countByEstadoId(Integer estadoId);
}
