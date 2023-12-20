package br.com.agostinho.algafood.api.controller;

import br.com.agostinho.algafood.domain.model.Estado;
import br.com.agostinho.algafood.domain.service.EstadosService;
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

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadosService estadoService;

    @GetMapping
    public List<Estado> listar(){
        return estadoService.buscarTodos();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Integer estadoId){
        return ResponseEntity.status(HttpStatus.OK).body(estadoService.buscarId(estadoId));
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.criar(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(@PathVariable Integer estadoId, @RequestBody Estado estado){
        return ResponseEntity.ok(estadoService.atualizar(estadoId, estado));
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Estado> apagar(@PathVariable Integer estadoId){
        estadoService.remover(estadoId);
        return ResponseEntity.noContent().build();
    }
}
