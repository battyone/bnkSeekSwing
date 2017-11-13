package com.cbr.bankseekswing.utils;

import com.cbr.bankseekswing.helper.BnkSeekHelper;
import com.cbr.bankseekswing.pojo.BnkSeek;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vassaeve
 */
public class Loader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);

    private static String[] excludedFields = {"VKEY", "NAMEN", "NEWKS", "PERMFO", "SROK", "AT1", "AT2", "CKS", "VKEYDEL", "DT_IZMR"};

    /**
     * Заполняем таблицу bnkseek данными
     *
     * @param dbfFile
     * @throws FileNotFoundException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void loadBnkSeek(File dbfFile) throws FileNotFoundException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {

        FileInputStream inputStream = new FileInputStream(dbfFile);
        DBFReader reader = null;
        DBFRow row;
        try {
            reader = new DBFReader(inputStream, Charset.forName("cp866"), false);
            while ((row = reader.nextRow()) != null) {
                BnkSeek bnk = BnkSeekHelper.createBnkSeekEntity(row);
                BnkDbUtils.createOneEntity(bnk);
            }
        } finally {
            DBFUtils.close(reader);
        }
    }
}
