package com.cbr.bankseekswing.helper;

import com.cbr.bankseekswing.pojo.ReferenceItem;
import com.cbr.bankseekswing.utils.EntityDescriptions;
import com.linuxense.javadbf.DBFRow;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by e.vassaev on 11/13/17.
 */
public class ReferenceItemHelper {

    private static ReferenceItem createReferenceEntity(Class<? extends ReferenceItem> cls, ExecutorInterface executorInterface) throws IllegalAccessException, InstantiationException {
        ReferenceItem entity = cls.newInstance();

        EntityDescriptions descriptionByEnum = EntityDescriptions.valueOf(cls.getSimpleName().toUpperCase());
        Map<String, EntityDescriptions.FieldDescription> descriptions = descriptionByEnum.getFieldsDescriptions();
        for (String field : descriptions.keySet()) {
            String value = executorInterface.getValue(field);
            FieldUtils.writeField(entity, field, value, true);
        }
        return entity;
    }

    public static ReferenceItem createReferenceEntity(Class<? extends ReferenceItem> cls, DBFRow row) throws IllegalAccessException, InstantiationException {
        return createReferenceEntity(cls, new ExecutorInterface() {
            @Override
            public String getValue(String field) {
                return row.getString(field);
            }
        });
    }

    public static ReferenceItem createReferenceEntity(Class<? extends ReferenceItem> cls, ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException {
        return createReferenceEntity(cls, new ExecutorInterface() {
            @Override
            public String getValue(String field) {
                try {
                    return rs.getString(field);
                } catch (Exception ee) {
                    return "";
                }
            }
        });
    }


    @FunctionalInterface
    public interface ExecutorInterface {
        String getValue(String field);
    }
}
