package br.com.agostinho.algafood.domain.service;

import br.com.agostinho.algafood.domain.model.Produto;
import br.com.agostinho.algafood.domain.repository.ProdutoRepository;
import br.com.agostinho.algafood.exceptions.ValidationException;
import br.com.agostinho.algafood.utils.BeansCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void remover(Integer produtoId){
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ValidationException("There's no produto found"));

        produtoRepository.delete(produto);
    }

    public Produto atualizar(Integer produtoId, Produto produto){
        Produto cidadeAtual = produtoRepository.findById(produtoId).orElseThrow(() -> new ValidationException("There's no produto found"));;

        BeansCopyUtils.copyOf(produto, cidadeAtual, "id");
        produtoRepository.save(cidadeAtual);

        return cidadeAtual;
    }

    public Produto buscarId(Integer produtoId){
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ValidationException("There's no produto found"));
    }

    public List<Produto> buscarTodas(){
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorNome(String nome){
        return produtoRepository.findByNomeContaining(nome);
    }
}
