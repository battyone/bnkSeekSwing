package com.cbr.bankseekswing.utils;

import org.junit.Test;

import java.util.Map;

/**
 * Created by e.vassaev on 11/9/17.
 */
public class EntityTests {

    @Test
    public void test1() {
        Map<String, EntitySqlData.Pair> descriptions = EntitySqlData.BNKSEEK.getDescriptions();

        assert descriptions != null;
        assert !descriptions.isEmpty();
        assert EntitySqlData.BNKSEEK.getPrimaryKeyFieldName() != null;

        //визуальная проверка
        descriptions.forEach((key, value) -> {
            System.out.println(key + ":" + value.getType().getTypeName() + ":" + value.getDescription());
        });

        System.out.println(EntitySqlData.BNKSEEK.getINSERT_SQL());
        System.out.println(EntitySqlData.BNKSEEK.getUPDATE_SQL());
        System.out.println(EntitySqlData.BNKSEEK.getDELETE_SQL());
    }


    @Test
    public void test2(){


    }

}
