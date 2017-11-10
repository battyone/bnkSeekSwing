package com.cbr.bankseekswing.utils;

import com.cbr.bankseekswing.annotation.Description;
import com.cbr.bankseekswing.pojo.BnkSeek;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by e.vassaev on 11/9/17.
 */
public enum EntitySqlData {

    /**
     * Наименование таблицы и pojo-класса
     */
    BNKSEEK(initDescriptions(BnkSeek.class));

    private final Map<String, Pair> descriptions;
//    private final String fields;
    private final String primaryKeyFieldName;
    private final String INSERT_SQL;
    private final String UPDATE_SQL;
    private final String DELETE_SQL;

    EntitySqlData(EntityDescription description) {

        this.descriptions = description.fieldsDescriptions;
        this.primaryKeyFieldName = description.getPrimaryKeyFieldName();

        String fields = String.join(", ", descriptions.keySet());
        String questions = String.join(",", Collections.nCopies(descriptions.size(), "?"));
        this.INSERT_SQL = String.format("INSERT INTO %s (%s) values (%s)", this.name(), fields, questions);

        String setClause = String.join(
                ", ",
                descriptions.keySet()
                        .stream()
                        .filter(
                                s -> {
                                    return !s.equals(primaryKeyFieldName);
                                }
                        ).map(
                                s -> {
                                    return s + "=?";
                                }
                        ).collect(Collectors.toList())
        );
        String whereClause = primaryKeyFieldName + "=?";

        this.UPDATE_SQL = String.format("UPDATE %s set %s WHERE %s", this.name(), setClause, whereClause);

        this.DELETE_SQL = String.format("DELETE FROM %s WHERE %s", this.name(), whereClause);
    }

//    /**
//     * Для целей формирования sql-запросов
//     *
//     * @return
//     */
//    public String getFields() {
//        return fields;
//    }
    public String getPrimaryKeyFieldName() {
        return primaryKeyFieldName;
    }

    public String getUPDATE_SQL() {
        return UPDATE_SQL;
    }

    public String getINSERT_SQL() {
        return INSERT_SQL;
    }

    public String getDELETE_SQL() {
        return DELETE_SQL;
    }

    public Map<String, Pair> getDescriptions() {
        return descriptions;
    }

    private static EntityDescription initDescriptions(Class<?> cls) {
        Map<String, Pair> result = new HashMap<>();
        String primaryKeyFieldName = null;
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            Description declaredAnnotation = field.getDeclaredAnnotation(Description.class);
            if (declaredAnnotation != null) {
                result.put(field.getName(), new Pair(declaredAnnotation.value(), field.getGenericType()));
                if (declaredAnnotation.PK() && primaryKeyFieldName == null) {
                    primaryKeyFieldName = field.getName();
                }
            }
        }
        return new EntityDescription(result, primaryKeyFieldName);
    }

    public static class Pair {

        private final String description;
        private final Type type;

        public Pair(String description, Type type) {
            this.description = description;
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public Type getType() {
            return type;
        }
    }

    private static class EntityDescription {

        private final Map<String, Pair> fieldsDescriptions;
        private final String primaryKeyFieldName;

        public EntityDescription(Map<String, Pair> fieldsDescriptions, String primaryKeyFieldName) {
            this.fieldsDescriptions = fieldsDescriptions;
            this.primaryKeyFieldName = primaryKeyFieldName;
        }

        public String getPrimaryKeyFieldName() {
            return primaryKeyFieldName;
        }

        public Map<String, Pair> getFieldsDescriptions() {
            return fieldsDescriptions;
        }
    }
}
