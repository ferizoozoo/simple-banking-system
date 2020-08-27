package banking;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

import org.sqlite.SQLiteDataSource;

public class Db {

    static String fileName;

    static void createTable() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            String sql = "CREATE TABLE card (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "number TEXT," +
                    "pin TEXT," +
                    "balance INTEGER DEFAULT 0" +
                    ");";
            try (Statement s = con.createStatement()) {
                s.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    static void saveRecord(String[] account) {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO card (number, pin, balance) " +
                    "VALUES ('" +
                    account[0] +
                    "', '" +
                    account[1] +
                    "', '" +
                    account[2] +
                    "');";
            try (Statement s = con.createStatement()) {
                s.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    static String[] getRecord(String accountNumber, String pin) {
        String[] result = null;
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            String sql = "SELECT * FROM card " +
                    "WHERE number = '" + accountNumber + "' AND " +
                    "pin = '" + pin + "';";
            try (Statement s = con.createStatement()) {
                try (ResultSet res = s.executeQuery(sql)) {
                    String balance = String.valueOf(res.getInt("balance"));
                    if (balance != null) {
                        result = new String[] {accountNumber, pin, balance};
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return result;
    }
}

