package com.cbr.bankseekswing.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

/**
 * Created by e.vassaev on 11/9/17.
 */
public class EntityTests {

    private static final Random rnd = new Random();
    
    public static String generateRandomStr() {
        return Long.toString(Math.abs(Math.round(rnd.nextLong() * (rnd.nextGaussian() + 0.1f))), 36);
    }

    @Test
    public void test1() {
        Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.BNKSEEK.getFieldsDescriptions();

        assert descriptions != null;
        assert !descriptions.isEmpty();
        assert EntityDescriptions.BNKSEEK.getPrimaryKeyFieldName() != null;

        //визуальная проверка
        descriptions.forEach((key, value) -> {
            System.out.println(key + ":" + value.getType().getTypeName() + ":" + value.getDescription());
        });

        System.out.println(EntityDescriptions.BNKSEEK.getINSERT_SQL());
        System.out.println(EntityDescriptions.BNKSEEK.getUPDATE_SQL());
        System.out.println(EntityDescriptions.BNKSEEK.getDELETE_SQL());
    }

}
