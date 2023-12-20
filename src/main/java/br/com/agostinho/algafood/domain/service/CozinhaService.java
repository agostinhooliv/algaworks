package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Cozinha;
import br.com.agostinho.algafood.domain.repository.CozinhaRepository;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha criar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void remover(Integer cozinhaId){
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new ValidationException("There's no cozinha found"));

        cozinhaRepository.delete(cozinha);
    }

    public Cozinha atualizar(Integer cozinhaId, Cozinha cozinha){
        Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new ValidationException("There's no cozinha found"));;

        BeansCopyUtils.copyOf(cozinha, cozinhaAtual, "id");
        cozinhaRepository.save(cozinhaAtual);

        return cozinhaAtual;
    }

    public Cozinha buscarId(Integer cozinhaId){
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new ValidationException("There's no cozinha found"));
    }

    public List<Cozinha> buscarTodas(){
        return cozinhaRepository.findAll();
    }
}
