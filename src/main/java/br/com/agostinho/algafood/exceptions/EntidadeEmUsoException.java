package br.com.agostinho.algafood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends NegocioException{

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}
