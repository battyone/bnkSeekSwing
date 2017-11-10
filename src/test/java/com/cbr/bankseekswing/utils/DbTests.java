package com.cbr.bankseekswing.utils;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void readDbfFile() {
        InputStream resourceAsStream = DbTests.class.getResourceAsStream("/BNKSEEK.DBF");
        Set<String> fieldsForLoad = new HashSet<>();

        DBFReader reader = null;
        try {

            reader = new DBFReader(resourceAsStream, Charset.forName("cp866"));
            int numberOfFields = reader.getFieldCount();
            String fieldName;
            for (int i = 0; i < numberOfFields; i++) {
                fieldName = reader.getField(i).getName().toUpperCase();
                fieldsForLoad.add(fieldName);
                System.out.print(fieldName.toLowerCase() + "\t");
            }
            System.out.println();
            DBFRow row;

            String[] fields = fieldsForLoad.toArray(new String[fieldsForLoad.size()]);

            while ((row = reader.nextRow()) != null) {
                for (String field : fields) {

                    System.out.print(row.getObject(field) + "\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            LOGGER.error(">>> ошибка:", e);
        } finally {
            DBFUtils.close(reader);
        }
    }
}
