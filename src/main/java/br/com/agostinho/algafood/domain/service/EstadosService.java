package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Estado;
import br.com.agostinho.algafood.domain.repository.EstadoRepository;
import br.com.agostinho.algafood.exceptions.EntidadeEmUsoException;
import br.com.agostinho.algafood.exceptions.EstadoNaoEncontradoException;
import br.com.agostinho.algafood.exceptions.NegocioException;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadosService {

    @Autowired
    private EstadoRepository estadoRepository;

    private static final String MSG_ESTADO_EM_USO
            = "Estado de código %d não pode ser removida, pois está em uso";

    public Estado criar(Estado estado){
        return estadoRepository.save(estado);
    }

    public void remover(Integer estadoId){

        try {
            Estado estado = estadoRepository.findById(estadoId)
                    .orElseThrow(() -> new ValidationException("There's no estado found"));

            estadoRepository.delete(estado);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }

    public Estado atualizar(Integer cidadeId, Estado estado){
        Estado estadoAtual = estadoRepository.findById(cidadeId).orElseThrow(() -> new ValidationException("There's no estado found"));;

        BeansCopyUtils.copyOf(estado, estadoAtual, "id");
        estadoRepository.save(estadoAtual);

        return estadoAtual;
    }

    public Estado buscarId(Integer estadoId){
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(String.format("Não existe um cadastro de estado com código %d", estadoId)));
    }

    public List<Estado> buscarTodos(){
        return estadoRepository.findAll();
    }
}
