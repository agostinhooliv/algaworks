package br.com.agostinho.algafood.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValidationProperties {

    public static Boolean validPropertyNull(Class aClass, Object o, String searchField) {

        return checkFieldsIsNull(aClass, o)
                .stream()
                .filter(field ->  Objects.equals(field.getName().equals(searchField), true))
                .findFirst()
                .isPresent();
    }

    private static List<Field> checkFieldsIsNull(Class<?> instance, Object o) {

        List<Field> fields = Arrays.asList(instance.getDeclaredFields());
        List<Field> emptyFields = new ArrayList<>();

        fields.forEach((field) ->{
            field.setAccessible(true);

            try {

            if(Objects.isNull(field.get(o))){
                emptyFields.add(field);
            }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return emptyFields;
    }
}
