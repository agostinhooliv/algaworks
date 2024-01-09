package br.com.agostinho.algafood.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private LocalDateTime dataHora;
    private String userMessage;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {

        private String nome;
        private String userMessage;
    }
}
