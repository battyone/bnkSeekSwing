package com.cbr.bankseekswing.dbview;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 * @param <T>
 */
public class MyTableModel<T> extends AbstractTableModel {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyTableModel.class);

    private static final long serialVersionUID = 8947197665669491108L;

    private List<ColumnDescription> columns;
    private List<T> objects;
    private final Class<T> entityClass;

    public MyTableModel(Class<T> entityClass) {
        columns = new ArrayList<>(0);
        objects = new ArrayList<>(0);
        this.entityClass = entityClass;
    }

    public void setRows(List<T> rows) {
        objects = new ArrayList<>(rows);
        fireTableDataChanged();
    }

    public void addRow(T row) {
        objects.add(row);
        fireTableRowsInserted(objects.size() - 1, objects.size() - 1);
    }

    public void addColumnDescription(ColumnDescription col) {
        columns.add(col);
    }

    public List<ColumnDescription> getColumnDescription() {
        return columns;
    }

    public void addColumnDescription(int order, String beanName, String colName) {
        columns.add(new ColumnDescription(order, beanName, colName));
    }

    public T getRow(int row) {
        if (objects != null) {
            if (row <= objects.size()) {
                return objects.get(row);
            }
        }
        return null;
    }

    @Override
    public int findColumn(String columnName) {
        for (ColumnDescription col : columns) {
            if (col.getColumnName().equalsIgnoreCase(columnName)) {
                return col.getOrder();
            }
        }
        return 0;
    }

    public String getColumnFieldName(int column) {
        for (ColumnDescription col : columns) {
            if (col.getOrder() == column) {
                return col.getFieldName();
            }

        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        for (ColumnDescription col : columns) {
            if (col.getOrder() == column) {
                return col.getColumnName();
            }
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //super.setValueAt(aValue, rowIndex, columnIndex); ???
    }

    @Override
    public int getRowCount() {
        return objects.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            String column_key = "";
            for (ColumnDescription col : columns) {
                if (col.getOrder() == columnIndex) {
                    column_key = col.getFieldName();
                    break;
                }
            }

            BeanInfo info = Introspector.getBeanInfo(entityClass);
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                String prop_name = pd.getName();
                if (column_key.equalsIgnoreCase(prop_name)) {
                    return pd.getPropertyType();
                }
            }
        } catch (IntrospectionException ex) {
            LOGGER.error("", ex);
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            String column_key = "";

            for (ColumnDescription col : columns) {
                if (col.getOrder() == columnIndex) {
                    column_key = col.getFieldName();
                    break;
                }
            }

            BeanInfo info = Introspector.getBeanInfo(entityClass); //refactor
            T obj = objects.get(rowIndex);

            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                String prop_name = pd.getName();
                if (column_key.equalsIgnoreCase(prop_name)) {
                    Method meth = pd.getReadMethod();
                    Object res = meth.invoke(obj, (Object[]) null);
                    return res;
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOGGER.error("", ex);
        }
        return "";
    }

    public void setRow(int selectedRow, T object) {
        objects.set(selectedRow, object);
        fireTableRowsUpdated(selectedRow, selectedRow);
    }

    public void removeRow(int selectedRow) {
        objects.remove(selectedRow);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }

    public List<T> getAllRows() {
        return objects;
    }

    public void setColumndescription(List<ColumnDescription> columnsDescription) {
        columns = new ArrayList<>(columnsDescription);
    }

}
