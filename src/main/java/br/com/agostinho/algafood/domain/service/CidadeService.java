package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Cidade;
import br.com.agostinho.algafood.domain.repository.CidadeRepository;
import br.com.agostinho.algafood.exceptions.EntidadeEmUsoException;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    public Cidade criar(Cidade cidade){
        return cidadeRepository.save(cidade);
    }

    public void remover(Integer cidadeId){

        try{

            Cidade cidade = cidadeRepository.findById(cidadeId)
                    .orElseThrow(() -> new ValidationException("There's no cidade found"));

            cidadeRepository.delete(cidade);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
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
