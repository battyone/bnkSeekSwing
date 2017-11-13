package com.cbr.bankseekswing.dbview;

/**
 *
 * @author vassaeve
 */
public class ColumnDescription {

    private final int order;
    private final String fieldName;
    private final String columnName;

    public ColumnDescription(int order, String fieldName, String columnName) {
        this.order = order;
        this.fieldName = fieldName;
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getOrder() {
        return order;
    }

}
