package br.com.agostinho.algafood;


import br.com.agostinho.algafood.domain.model.Cozinha;
import br.com.agostinho.algafood.domain.service.CozinhaService;

import br.com.agostinho.algafood.exceptions.ValidationException;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CadastroCozinhaIT {

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    public void testarCadastroCozinhaComSucesso(){
        //cenario
        Cozinha cozinha = new Cozinha();
                cozinha.setNome("Teste");


        //acao
        cozinha = cozinhaService.criar(cozinha);

        //validacao
        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isNotNull();
    }

    @Test
    public void testarCadastroCozinhaSemNome(){
        //cenario
        Cozinha cozinha = new Cozinha();

        //acao
        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cozinhaService.criar(cozinha);
                });

        //validacao
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(3L);
        DataIntegrityViolationException erroEsperado =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
                    cozinhaService.remover(cozinha.getId().intValue());
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(300L);
        ValidationException erroEsperado =
                Assertions.assertThrows(ValidationException.class, () -> {
                    cozinhaService.remover(cozinha.getId().intValue());
                });

        assertThat(erroEsperado).isNotNull();

    }
}
