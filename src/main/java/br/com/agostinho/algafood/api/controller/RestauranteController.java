package br.com.agostinho.algafood.api.controller;

import br.com.agostinho.algafood.domain.model.Restaurante;
import br.com.agostinho.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.buscarTodos();
    }

    @GetMapping("/all-criteria")
    public List<Restaurante> listarCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteService.buscarCriteria(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Integer restauranteId){
        return ResponseEntity.status(HttpStatus.OK).body(restauranteService.buscarPorId(restauranteId));
    }

    @GetMapping("/com-frete-gratis")
    public ResponseEntity<List<Restaurante>> buscarTodosFreteGratis(String nome){
        return ResponseEntity.status(HttpStatus.OK).body(restauranteService.buscarTodosSpecification(nome));
    }

    @GetMapping("/por-taxaFrete")
    public ResponseEntity<List<Restaurante>> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return ResponseEntity.ok().body(restauranteService.buscarPorTaxaFrete(taxaInicial, taxaFinal));
    }

    @GetMapping("/por-nomeCozinha")
    public ResponseEntity<List<Restaurante>> buscarPorNomeCozinha(String nome, Integer cozinhaId){
        return ResponseEntity.ok().body(restauranteService.buscarPorNomeCozinha(nome, cozinhaId));
    }

    @GetMapping("/por-nomeTaxaFrete")
    public ResponseEntity<List<Restaurante>> buscarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        return ResponseEntity.ok().body(restauranteService.buscarPorNomeTaxaFrete(nome, taxaInicial, taxaFinal));
    }

    @GetMapping("/primeiro")
    public ResponseEntity<Restaurante> buscarPrimeiro(){
        return ResponseEntity.ok().body(restauranteService.buscarPrimeiro());
    }

    @PostMapping
    public ResponseEntity<Restaurante> salvar(@RequestBody @Valid Restaurante restaurante){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.criar(restaurante));
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Integer restauranteId, @RequestBody Restaurante restaurante){
        return ResponseEntity.ok(restauranteService.atualizar(restauranteId, restaurante));
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> apagar(@PathVariable Integer restauranteId){
        restauranteService.remover(restauranteId);
        return ResponseEntity.noContent().build();
    }
}
