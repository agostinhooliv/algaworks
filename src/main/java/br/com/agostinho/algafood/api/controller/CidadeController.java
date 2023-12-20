package br.com.agostinho.algafood.api.controller;

import br.com.agostinho.algafood.domain.model.Cidade;
import br.com.agostinho.algafood.domain.model.Estado;
import br.com.agostinho.algafood.domain.service.CidadeService;
import br.com.agostinho.algafood.domain.service.EstadosService;
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
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {


    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private EstadosService estadosService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.buscarTodas();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cidade> buscar(@PathVariable Integer cozinhaId) {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscarId(cozinhaId));
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Cidade>> cidadesPorNome(String nome){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscarPorNome(nome));
    }

    @GetMapping("/por-nomeEstadoId")
    public ResponseEntity<List<Cidade>> cidadesPorNomeAndEstadoId(String nome, Integer estadoId){
        return ResponseEntity.ok().body(cidadeService.buscarPorNomeAndEstadoId(nome, estadoId));
    }

    @GetMapping("/primeiroPorNome")
    public ResponseEntity<Optional<Cidade>> primeiraCidadePorNome(String nome){
        return ResponseEntity.ok(cidadeService.buscarPrimeiroPorNome(nome));
    }

    @GetMapping("/top2PorNome")
    public ResponseEntity<List<Cidade>> top2PorNome(String nome){
        return ResponseEntity.ok(cidadeService.buscarPorNomeTop2(nome));
    }

    @GetMapping("/contaPorEstado")
    public ResponseEntity<Integer> contarPorEstado(Integer estadoId){
        return ResponseEntity.ok(cidadeService.contarPorEstado(estadoId));
    }

    @PostMapping
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade) {

        estadosService.buscarId(cidade.getEstado().getId().intValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.criar(cidade));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Integer cozinhaId, @RequestBody Cidade cidade) {
        return ResponseEntity.ok(cidadeService.atualizar(cozinhaId, cidade));
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cidade> apagar(@PathVariable Integer cozinhaId) {
        cidadeService.remover(cozinhaId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cidadeId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Integer cidadeId,
                                              @RequestBody Map<String, Object> campos) {
        Cidade cidadeAtual = cidadeService.buscarId(cidadeId);

        if (Objects.isNull(cidadeAtual)) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, cidadeAtual);

        return atualizar(cidadeId, cidadeAtual);
    }

    private void merge(Map<String, Object> dadosOrigem, Cidade cidadeDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Cidade cidadeOrigem = objectMapper.convertValue(dadosOrigem, Cidade.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Cidade.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, cidadeOrigem);

            ReflectionUtils.setField(field, cidadeDestino, novoValor);
        });
    }
}
