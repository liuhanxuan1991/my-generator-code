package com.lhx.study.mygeneratorcode.utils;

import com.lhx.study.mygeneratorcode.entity.bo.MysqlDbColumn;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库查询
 */
public class DbHelperUtil {


    /**
     * 根据库名查询所有表名
     * @param schema
     * @param connection
     * @return
     * @throws Exception
     */
    public static List<String> getDbTable(String schema, Connection connection) throws Exception {
        List<String> tableList = new ArrayList();
        String sql = "SELECT * FROM information_schema.tables WHERE table_schema = ?";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setString(1, schema);
        ResultSet rs = pre.executeQuery();

        while(rs.next()) {
            tableList.add(rs.getString("table_name"));
        }

        return tableList;
    }

    /**
     * 根据库名，表名，查询所有字段
     * @param schema
     * @param table
     * @param connection
     * @return
     * @throws Exception
     */
    public static List<MysqlDbColumn> getTableColumns(String schema, String table, Connection connection) throws Exception {
        List<MysqlDbColumn> columnList = new ArrayList();
        String sql = "SELECT DISTINCT * FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name = ? ORDER BY ORDINAL_POSITION";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setString(1, schema);
        pre.setString(2, table);
        ResultSet rs = pre.executeQuery();

        while(rs.next()) {
            MysqlDbColumn column = new MysqlDbColumn();
            columnList.add(buildDbColum(schema, table, rs, column));
        }

        return columnList;
    }

    /**
     * 获取表以及表字段
     * @param schema
     * @param connection
     * @return
     * @throws Exception
     */
    public static Map<String, List<MysqlDbColumn>> getMutilTableColumns(String schema, Connection connection) throws Exception {
        Map<String, List<MysqlDbColumn>> tableMap = new HashMap();
        String sql = "SELECT DISTINCT * FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name in (SELECT table_name FROM information_schema.tables WHERE table_schema = ?) ORDER BY table_name, ORDINAL_POSITION";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setString(1, schema);
        pre.setString(2, schema);
        ResultSet rs = pre.executeQuery();
        List<MysqlDbColumn> columnList = new ArrayList();
        String tempName = "";
        String tname = "";

        while(rs.next()) {
            tname = rs.getString("table_name");
            if (!tempName.equals(tname)) {
                if (CollectionUtils.isNotEmpty(columnList)) {
                    tableMap.put(tempName, columnList);
                }

                tempName = tname;
                columnList = new ArrayList();
            }

            MysqlDbColumn column = new MysqlDbColumn();
            columnList.add(buildDbColum(schema, tname, rs, column));
            if (CollectionUtils.isNotEmpty(columnList)) {
                tableMap.put(tempName, columnList);
            }
        }

        return tableMap;
    }

    /**
     * 获取表字段
     * @param schema
     * @param table
     * @param rs
     * @param column
     * @return
     * @throws Exception
     */
    public static MysqlDbColumn buildDbColum(String schema, String table, ResultSet rs, MysqlDbColumn column) throws Exception {
        column.setTableSchema(schema);
        column.setTableName(table);
        column.setColumnName(rs.getString("column_name"));
        column.setColumnType(rs.getString("column_type"));
        column.setColumnDefault(rs.getObject("column_default"));
        column.setColumnKey(rs.getString("column_key"));
        column.setColumnComment(rs.getString("column_comment"));
        column.setDataType(rs.getString("data_type"));
        column.setCharacterMaximumLength(rs.getLong("character_maximum_length"));
        column.setCharacterOctetLength(rs.getLong("character_octet_length"));
        column.setExtra(rs.getString("extra"));
        column.setIsNullable(rs.getString("is_nullable"));
        column.setPrivileges(rs.getString("privileges"));
        column.setOrdinalPosition(rs.getInt("ordinal_position"));
        column.setNumericPrecision(rs.getLong("numeric_precision"));
        column.setNumericScale(rs.getLong("numeric_scale"));
        column.setCharacterSetName(rs.getString("character_set_name"));
        column.setCollationName(rs.getString("collation_name"));
        return column;
    }
}
