package br.com.agostinho.algafood.api.controller;

import br.com.agostinho.algafood.domain.model.Produto;
import br.com.agostinho.algafood.domain.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService prodiutoService;

    @GetMapping
    public List<Produto> listar() {
        return prodiutoService.buscarTodas();
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> buscar(@PathVariable Integer produtoId) {
        return ResponseEntity.status(HttpStatus.OK).body(prodiutoService.buscarId(produtoId));
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Produto>> produtosPorNome(String nome){
        return ResponseEntity.status(HttpStatus.OK).body(prodiutoService.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prodiutoService.criar(produto));
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<Produto> atualizar(@PathVariable Integer produtoId, @RequestBody Produto produto) {
        return ResponseEntity.ok(prodiutoService.atualizar(produtoId, produto));
    }

    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Produto> apagar(@PathVariable Integer produtoId) {
        prodiutoService.remover(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{produtoId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Integer produtoId,
                                              @RequestBody Map<String, Object> campos) {
        Produto produtoAtual = prodiutoService.buscarId(produtoId);

        if (Objects.isNull(produtoAtual)) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, produtoAtual);

        return atualizar(produtoId, produtoAtual);
    }

    private void merge(Map<String, Object> dadosOrigem, Produto cidadeDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Produto cidadeOrigem = objectMapper.convertValue(dadosOrigem, Produto.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Produto.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, cidadeOrigem);

            ReflectionUtils.setField(field, cidadeDestino, novoValor);
        });
    }
}
