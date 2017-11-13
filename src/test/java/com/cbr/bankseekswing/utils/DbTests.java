package com.cbr.bankseekswing.utils;

import com.cbr.bankseekswing.helper.BnkSeekHelper;
import com.cbr.bankseekswing.helper.ReferenceItemHelper;
import com.cbr.bankseekswing.pojo.BnkSeek;
import com.cbr.bankseekswing.pojo.Pzn;
import com.cbr.bankseekswing.pojo.ReferenceItem;
import com.cbr.bankseekswing.pojo.Reg;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by e.vassaev on 11/9/17.
 */
public class DbTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbTests.class);

    @Test
    public void getConnection() {
        try {
            Connection connection = BnkDbUtils.getConnection();
            assert connection != null;
        } catch (Exception ex) {
            LOGGER.error(">>> ошибка:", ex);
        }
    }

    @Test
    public void testReferenceIUtem() {

        Map<String, Class<? extends ReferenceItem>> mapka = new HashMap<>();
        mapka.put("/PZN.DBF", Pzn.class);
        mapka.put("/REG.DBF", Reg.class);

        for(String key : mapka.keySet()) {
            System.out.println("***********"+key+"***********");
            InputStream resourceAsStream = DbTests.class.getResourceAsStream(key);
            Class<? extends ReferenceItem> cls = mapka.get(key);
            DBFReader reader = null;
            try {

                reader = new DBFReader(resourceAsStream, Charset.forName("cp866"));
                DBFRow row;
                while ((row = reader.nextRow()) != null) {
                    ReferenceItem item = ReferenceItemHelper.createReferenceEntity(cls, row);
                    System.out.println(item.asString());
                }
            } catch (Exception e) {
                LOGGER.error(">>> ошибка:", e);
                e.printStackTrace();
                assert false;
            } finally {
                DBFUtils.close(reader);
            }
        }
    }

    @Test
    public void readDbfFileCheckFields() {
        String excludedFieldsStr = "," + String.join(",", new String[]{"VKEY", "NAMEN", "NEWKS", "PERMFO", "SROK", "AT1", "AT2", "CKS", "VKEYDEL", "DT_IZMR"}) + ",";

        InputStream resourceAsStream = DbTests.class.getResourceAsStream("/BNKSEEK.DBF");
        Set<String> fieldsForLoad = new HashSet<>();

        Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.BNKSEEK.getFieldsDescriptions();

        DBFReader reader = null;
        try {

            reader = new DBFReader(resourceAsStream, Charset.forName("cp866"));
            int numberOfFields = reader.getFieldCount();
            String fieldName;
            for (int i = 0; i < numberOfFields; i++) {
                fieldName = reader.getField(i).getName().toUpperCase();
                if (!excludedFieldsStr.contains("," + fieldName + ",")) {
                    fieldsForLoad.add(fieldName);
                }
                System.out.print(fieldName.toLowerCase() + "\t");
            }

            assert fieldsForLoad != null;
            assert !fieldsForLoad.isEmpty();
            assert fieldsForLoad.size() == descriptions.size();

            System.out.println();
            DBFRow row;

            while ((row = reader.nextRow()) != null) {
                BnkSeek bnk = BnkSeekHelper.createBnkSeekEntity(row);
                assert bnk.getNewnum() != null && !StringUtils.isEmpty(bnk.getNewnum());
                System.out.println(bnk);
            }

        } catch (Exception e) {
            LOGGER.error(">>> ошибка:", e);
            e.printStackTrace();
            assert false;
        } finally {
            DBFUtils.close(reader);
        }
    }
}
