package br.com.agostinho.algafood.domain.repository;

import br.com.agostinho.algafood.domain.model.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){

        var jpql = new StringBuilder("from Restaurante where 0 = 0");
        var parametros = new HashMap<String, Object>();

        if(StringUtils.hasLength(nome)){
            jpql.append("nome like :nome ");
            parametros.put("nome", "%"+ nome+"%");
        }

        if(Objects.nonNull(taxaInicial)){
            jpql.append(" and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaInicial);
        }

        if(Objects.nonNull(taxaFinal)){
            jpql.append(" and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFinal);
        }

        TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findByCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);

        Root<Restaurante> restauranteRoot = criteriaQuery.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasLength(nome)){
           predicates.add(criteriaBuilder.like(restauranteRoot.get("nome"), "%"+ nome+"%"));
        }

        if(Objects.nonNull(taxaInicial)){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(restauranteRoot.get("taxaFrete"), taxaInicial));
        }

        if(Objects.nonNull(taxaFinal)){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(restauranteRoot.get("taxaFrete"), taxaFinal));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = entityManager.createQuery(criteriaQuery);


        return query.getResultList();
    }
}
