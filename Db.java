package banking;

import java.sql.*;

import org.sqlite.SQLiteDataSource;

public class Db {

    static String fileName;

    static void update(String sql) {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            try (Statement s = con.createStatement()) {
                s.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    static void createTable() {
        String sql = "CREATE TABLE card (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "number TEXT," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0" +
                ");";
        update(sql);
    }

    static void saveRecord(String[] account) {
        String sql = "INSERT INTO card (number, pin, balance) " +
                "VALUES ('" +
                account[0] +
                "', '" +
                account[1] +
                "', '" +
                account[2] +
                "');";
        update(sql);
    }

    static void addIncomeUpdateRecord(String accountNumber, String addIncome) {
        String sql = "UPDATE card" +
                "SET balance = balance + " + addIncome + " " +
                "WHERE number = " + accountNumber + ";";
        update(sql);
    }

    static void withDrawUpdateRecord(String accountNumber, String amount) {
        String sql = "UPDATE card" +
                "SET balance = balance - amount " +
                "WHERE number = " + accountNumber + ";";
        update(sql);
    }

    static void deleteRecord(String accountNumber, String pin) {
        String sql = "DELETE FROM card " +
                "WHERE number = " + accountNumber + " " +
                "AND pin = " + pin + " ;";
        update(sql);
    }

    static String[] getRecord(String accountNumber, String pin) {
        String[] result = null;
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            try (Statement s = con.createStatement()) {
                String sql = "SELECT * FROM card " +
                        "WHERE number = '" + accountNumber + "' AND " +
                        "pin = '" + pin + "';";
                try (ResultSet res = s.executeQuery(sql)) {
                    String balance = String.valueOf(res.getInt("balance"));
                    result = new String[] {accountNumber, pin, balance};
                }
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return result;
    }

    static boolean checkAccount(String accountNumber) {
        boolean doesExist = false;
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            try (Statement s = con.createStatement()) {
                String sql = "SELECT * FROM card " +
                        "WHERE number = '" + accountNumber + "';";
                try (ResultSet res = s.executeQuery(sql)) {
                    if (!res.wasNull()){
                        doesExist = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return doesExist;
    }

    static String checkAmount(String accountNumber) {
        String amount = null;
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + fileName);
        try (Connection con = ds.getConnection()) {
            try (Statement s = con.createStatement()) {
                String sql = "SELECT (balance)" +
                        "FROM card " +
                        "WHERE number = " + accountNumber;
                try (ResultSet res = s.executeQuery(sql)) {
                    if (res.next()){
                        amount = res.getString("balance");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return amount;

    }

    static void doTransfer(String sourceNumber, String destNumber, String amount) {
        withDrawUpdateRecord(sourceNumber, amount);
        addIncomeUpdateRecord(destNumber, amount);
    }
}
