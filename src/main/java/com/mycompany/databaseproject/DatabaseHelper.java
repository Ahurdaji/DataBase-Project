package com.mycompany.databaseproject;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.security.MessageDigest;

/**
 *
 * @author hadalkharouf
 */
public class DatabaseHelper {

    // returns JDBC Conn using database connection class
    public static Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // hashing ensures secure authentication, 
    // while role based access control manages authorization
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();

        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // SELECT queries -- we use this for login authentication 
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        setParams(stmt, params);
        return stmt.executeQuery();
    }

    // INSERT / UPDATE / DELETE -- when modifiying database data
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        setParams(stmt, params);
        return stmt.executeUpdate();
    }

    // Fill JTable with result of SELECT -- the result of SELECT query
    public static void fillTable(JTable table, String sql, Object... params) {
        try (ResultSet rs = executeQuery(sql, params)) {
            DefaultTableModel model = new DefaultTableModel();
            int columnCount = rs.getMetaData().getColumnCount();

            // read columns names dynamically
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rs.getMetaData().getColumnName(i));
            }

            // Add rows
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method inserts a record and returns the generated PK
    public static int executeInsertAndReturnId(String sql, Object... params) throws Exception {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

        throw new Exception("Failed to get generated ID");
    }

    public static void updateContractStatus(int contractId) {
        try {
            // 1. COMPLETED → all installments paid
            String completedSql
                    = "UPDATE HireContract "
                    + "SET StatusID = 2 "
                    + "WHERE ContractID = ? "
                    + "AND NOT EXISTS ( "
                    + "   SELECT 1 FROM InstallmentPayment "
                    + "   WHERE ContractID = ? AND IsPaid = 0 "
                    + ")";

            executeUpdate(completedSql, contractId, contractId);

            // 2. LATE → unpaid and overdue
            String lateSql
                    = "UPDATE HireContract "
                    + "SET StatusID = 3 "
                    + "WHERE ContractID = ? "
                    + "AND EXISTS ( "
                    + "   SELECT 1 FROM InstallmentPayment "
                    + "   WHERE ContractID = ? AND IsPaid = 0 AND DueDate < GETDATE() "
                    + ")";

            executeUpdate(lateSql, contractId, contractId);

            // 3. ACTIVE → unpaid but not overdue
            String activeSql
                    = "UPDATE HireContract "
                    + "SET StatusID = 1 "
                    + "WHERE ContractID = ? "
                    + "AND EXISTS ( "
                    + "   SELECT 1 FROM InstallmentPayment "
                    + "   WHERE ContractID = ? AND IsPaid = 0 AND DueDate >= GETDATE() "
                    + ")";

            executeUpdate(activeSql, contractId, contractId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
    // يرجّع رقم واحد مثل COUNT(*) أو SUM(...)

    public static int getInt(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParams(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

}
