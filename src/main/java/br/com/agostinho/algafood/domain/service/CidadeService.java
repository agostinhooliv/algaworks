package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Cidade;
import br.com.agostinho.algafood.domain.repository.CidadeRepository;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade criar(Cidade cidade){
        return cidadeRepository.save(cidade);
    }

    public void remover(Integer cidadeId){
        Cidade cidade = cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new ValidationException("There's no cidade found"));

        cidadeRepository.delete(cidade);
    }

    public Cidade atualizar(Integer cidadeId, Cidade cidade){
        Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElseThrow(() -> new ValidationException("There's no cidade found"));;

        BeansCopyUtils.copyOf(cidade, cidadeAtual, "id");
        cidadeRepository.save(cidadeAtual);

        return cidadeAtual;
    }

    public Cidade buscarId(Integer cidadeId){
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new ValidationException("There's no cidade found"));
    }

    public List<Cidade> buscarTodas(){
        return cidadeRepository.findAll();
    }

    public List<Cidade> buscarPorNome(String nome){
        return cidadeRepository.findByNomeContaining(nome);
    }

    public Optional<Cidade> buscarPrimeiroPorNome(String nome){
        return cidadeRepository.findFirstCidadeByNomeContaining(nome);
    }

    public List<Cidade> buscarPorNomeAndEstadoId(String nome, Integer estadoId){
        return cidadeRepository.findByNomeContainingAndEstadoId(nome, estadoId);
    }
    public List<Cidade> buscarPorNomeTop2(String nome){
        return cidadeRepository.findTop2ByNomeContaining(nome);
    }

    public Integer contarPorEstado(Integer estadoId){
        return cidadeRepository.countByEstadoId(estadoId);
    }
}
