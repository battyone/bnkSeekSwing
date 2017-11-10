package com.cbr.bankseekswing.utils;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vassaeve
 */
public class Loader {

    private static String[] excludedFields = {"VKEY", "NAMEN", "NEWKS", "PERMFO", "SROK", "AT1", "AT2", "CKS", "VKEYDEL", "DT_IZMR"};

    /**
     * Заполняем таблицу bnkseek данными
     *
     * @param dbfFile
     * @throws FileNotFoundException
     */
    public static void loadBnkSeek(File dbfFile) throws FileNotFoundException {

        Set<String> fieldsForLoad = new HashSet<>();
        String excludedFieldsStr = "," + String.join(",", excludedFields) + ",";


        FileInputStream inputStream = new FileInputStream(dbfFile);
        //TODO определять кодировку? кодировка CP866!!!
        DBFReader reader = new DBFReader(inputStream, Charset.forName("cp866"), false);
        int numberOfFields = reader.getFieldCount();

        String fieldName;
        for (int i = 0; i < numberOfFields; i++) {
            fieldName = reader.getField(i).getName().toUpperCase();
            if (!excludedFieldsStr.contains(fieldName)) {
                fieldsForLoad.add(fieldName);
            }
        }

        DBFRow row;

        String[] fields = fieldsForLoad.toArray(new String[fieldsForLoad.size()]);
        while ((row = reader.nextRow()) != null) {
            for (String field : fields) {

                System.out.print(row.getObject(field) + "\t");

            }
            System.out.println();
        }

        DBFUtils.close(reader);
    }
}
