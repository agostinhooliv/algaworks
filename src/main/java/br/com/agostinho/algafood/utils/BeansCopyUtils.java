package br.com.agostinho.algafood.utils;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;

@Data
public class BeansCopyUtils {

    private Class primeiraClasse;
    private Class segundaClasse;

    public static Object copyOf(Object primeiraClasse, Object segundaClasse, String... parametro){

            BeanUtils.copyProperties(primeiraClasse, segundaClasse, parametro);

        return segundaClasse;
    }
}
