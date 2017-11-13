package com.cbr.bankseekswing.utils;

import com.cbr.bankseekswing.helper.BnkSeekHelper;
import com.cbr.bankseekswing.helper.ReferenceItemHelper;
import com.cbr.bankseekswing.pojo.BnkSeek;
import com.cbr.bankseekswing.pojo.ReferenceItem;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.codehaus.plexus.util.StringUtils;
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

    public static int countBnkSeek() throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            rs = conn.createStatement().executeQuery("select count(*) from BnkSeek");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void editOneEntity(BnkSeek entity) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(EntityDescriptions.BNKSEEK.getUPDATE_SQL());

            int i = updatePs(ps, entity, true);
            ps.setString(i, FieldUtils.readField(entity, EntityDescriptions.BNKSEEK.getPrimaryKeyFieldName(), true).toString());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void createOneEntity(BnkSeek entity) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(EntityDescriptions.BNKSEEK.getINSERT_SQL());

            updatePs(ps, entity, true);

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    private static int updatePs(PreparedStatement ps, BnkSeek entity, boolean excludePK) throws IllegalAccessException, SQLException {
        Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.BNKSEEK.getFieldsDescriptions();
        String pkfieldName = EntityDescriptions.BNKSEEK.getPrimaryKeyFieldName();
        EntityDescriptions.FieldDescription fieldDescription;

        int i = 1;
        for (String field : descriptions.keySet()) {
            if (excludePK && field.equals(pkfieldName)) {
                //? todo
            } else {
                fieldDescription = descriptions.get(field);
                Object value = FieldUtils.readField(entity, field, true);
                switch (fieldDescription.getType().getTypeName()) {
                    case "java.lang.String":
                        ps.setString(i, value.toString());//???
                        break;
                    default:
                        ps.setObject(i, value);
                }
                i++;
            }
        }
        return i;
    }

    public static List<ReferenceItem> loadAllReferenceItem(Class<? extends ReferenceItem> cls) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException, InstantiationException {
        Connection conn = null;
        ResultSet rs = null;
        List<ReferenceItem> result = new ArrayList<>(0);
        try {
            conn = getConnection();
            rs = conn.createStatement().executeQuery("select * from " + cls.getSimpleName());
            while (rs.next()) {
                result.add(ReferenceItemHelper.createReferenceEntity(cls, rs));
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public static List<BnkSeek> loadAllBnkSeek(Map<String, String> filter) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        ResultSet rs = null;
        List<BnkSeek> result = new ArrayList<>(0);
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("select * from BNKSEEK ");
            if (filter != null) {
                sql.append(" WHERE ");
                StringBuilder whereClause = new StringBuilder();
                filter.forEach((t, u) -> {
                    if (!StringUtils.isEmpty(t) && !StringUtils.isEmpty(u)) {
                        whereClause.append(" and ").append(t).append(" like '%").append(u).append("%'");
                    }
                });
                sql.append(whereClause.substring(" and ".length()));
            }
            rs = conn.createStatement().executeQuery(sql.toString());
            while (rs.next()) {
                result.add(BnkSeekHelper.createBnkSeekEntity(rs));
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public static void deleteOneEntity(BnkSeek row) throws ClassNotFoundException, SQLException, IllegalAccessException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(EntityDescriptions.BNKSEEK.getDELETE_SQL());
            String pkValue = String.valueOf(FieldUtils.readField(row, EntityDescriptions.BNKSEEK.getPrimaryKeyFieldName(), true));
            ps.setString(1, pkValue);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

    }

}
