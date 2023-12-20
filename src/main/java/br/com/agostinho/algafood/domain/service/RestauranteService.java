package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Restaurante;
import br.com.agostinho.algafood.domain.repository.RestauranteRepository;
import br.com.agostinho.algafood.domain.repository.spec.RestauranteSpecs;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestauranteService {

    public static final String THERE_S_NO_RESTAURANTE_FOUND = "There's no restaurante found";

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }

    public List<Restaurante> buscarTodosSpecification(String nome){
        return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhannte(nome)));
    }

    public Restaurante buscarPorId(Integer restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        THERE_S_NO_RESTAURANTE_FOUND));
    }

    public List<Restaurante> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    public List<Restaurante> buscarPorNomeCozinha(String nome, Integer cozinhaId) {
        return restauranteRepository.buscarPorNomeCozinha(nome, cozinhaId);
    }

    public List<Restaurante> buscarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }
    public List<Restaurante> buscarCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByCriteria(nome, taxaInicial, taxaFinal);
    }

    public Restaurante buscarPrimeiro(){
        return restauranteRepository.findFirst().orElseThrow(() -> new ValidationException("No restaurant is present."));
    }

    public Restaurante criar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Integer restauranteId, Restaurante restaurante) {

        Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElseThrow(() -> new ValidationException(THERE_S_NO_RESTAURANTE_FOUND));

        BeansCopyUtils.copyOf(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");
        return restauranteRepository.save(restauranteAtual);
    }

    public void remover(Integer restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ValidationException(THERE_S_NO_RESTAURANTE_FOUND));
        restauranteRepository.delete(restaurante);
    }
}
