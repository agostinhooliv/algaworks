package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Estado;
import br.com.agostinho.algafood.domain.repository.EstadoRepository;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadosService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado criar(Estado estado){
        return estadoRepository.save(estado);
    }

    public void remover(Integer estadoId){
        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new ValidationException("There's no estado found"));

        estadoRepository.delete(estado);
    }

    public Estado atualizar(Integer cidadeId, Estado estado){
        Estado estadoAtual = estadoRepository.findById(cidadeId).orElseThrow(() -> new ValidationException("There's no estado found"));;

        BeansCopyUtils.copyOf(estado, estadoAtual, "id");
        estadoRepository.save(estadoAtual);

        return estadoAtual;
    }

    public Estado buscarId(Integer estadoId){
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new ValidationException("There's no estado found"));
    }

    public List<Estado> buscarTodos(){
        return estadoRepository.findAll();
    }
}
