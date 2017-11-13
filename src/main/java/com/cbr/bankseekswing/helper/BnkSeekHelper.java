package com.cbr.bankseekswing.helper;

import com.cbr.bankseekswing.pojo.BnkSeek;
import com.cbr.bankseekswing.utils.EntityDescriptions;
import com.linuxense.javadbf.DBFRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 *
 * @author e.vassaev
 */
public class BnkSeekHelper {

    public static BnkSeek createBnkSeekEntity(DBFRow row) throws IllegalAccessException {
        BnkSeek entity = new BnkSeek();

        Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.BNKSEEK.getFieldsDescriptions();
        EntityDescriptions.FieldDescription fieldDescription;
        Object value;
        for (String field : descriptions.keySet()) {
            fieldDescription = descriptions.get(field);
            switch (fieldDescription.getType().getTypeName()) {
                case "java.lang.String":
                    value = row.getString(field);
                    break;
                case "java.sql.Date":
                    java.util.Date temp = row.getDate(field);
                    if (temp != null) {
                        value = new java.sql.Date(temp.getTime());
                    } else {
                        value = null;
                    }
                    break;
                default:
                    value = row.getObject(field);
            }
            FieldUtils.writeField(entity, field, value, true);
        }
        return entity;
    }

    public static BnkSeek createBnkSeekEntity(ResultSet rs) throws SQLException, IllegalAccessException {
        BnkSeek entity = new BnkSeek();

        Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.BNKSEEK.getFieldsDescriptions();
        EntityDescriptions.FieldDescription fieldDescription;
        Object value;
        for (String field : descriptions.keySet()) {
            fieldDescription = descriptions.get(field);
            switch (fieldDescription.getType().getTypeName()) {
                case "java.lang.String":
                    value = rs.getString(field);
                    break;
                default:
                    value = rs.getObject(field);
            }
            FieldUtils.writeField(entity, field, value, true);
        }
        return entity;
    }
}
