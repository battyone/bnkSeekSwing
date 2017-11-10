package com.cbr.bankseekswing.utils;

import com.cbr.bankseekswing.pojo.BnkSeek;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vassaeve
 */
public class BnkDbUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BnkDbUtils.class);
    
    private static String className;
    private static String user;
    private static String password;
    private static String url;

    /**
     * @return соединение к БД
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        InputStream is = BnkDbUtils.class.getResourceAsStream("/liquibase.properties");
        Properties connectionProperties = new Properties();
        connectionProperties.load(is);

        className = connectionProperties.getProperty("driver");
        user = connectionProperties.getProperty("username");
        password = connectionProperties.getProperty("password");
        url = connectionProperties.getProperty("url");

        Class.forName(className);
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);

        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    //TODO переделать под все entity
    public static void createEntity(BnkSeek entity) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();

            ps = conn.prepareStatement(EntitySqlData.BNKSEEK.getINSERT_SQL());
            Map<String, EntitySqlData.Pair> descriptions = EntitySqlData.BNKSEEK.getDescriptions();
            EntitySqlData.Pair pair;
            int i = 1;
            for (String field : descriptions.keySet()) {
                pair = descriptions.get(field);
                Object value = FieldUtils.readField(entity, field);
                switch (pair.getType().getTypeName()) {
                    case "java.lang.String":
                        ps.setString(i, value.toString());//???
                        break;
                    default:
                        ps.setObject(i, value);
                }
                i++;
            }

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }

    }


}
