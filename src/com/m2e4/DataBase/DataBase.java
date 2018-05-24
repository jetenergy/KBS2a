package com.m2e4.DataBase;

import java.sql.*;
import java.util.ArrayList;

//JavaUsr //javakbs2a

public class DataBase {

    static private Connection connect = null;
    static private Statement statement = null;
    static private PreparedStatement preparedStatement = null;
    static private ResultSet resultSet = null;

    static public ArrayList<Product> products = new ArrayList<>();

    static public void connectDataBase() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://rene-home.myddns.me/KBS2A?user=JavaUsr&password=javakbs2a");
    }

    static public ArrayList<Product> ConnGetProducts() throws SQLException {
        statement = connect.createStatement();
        String querry = "select Naam, Hoogte, Breedte, X, Y from ProductOpslag JOIN Product ON ProductOpslag.ProductId = Product.ProductId;";
        resultSet = statement.executeQuery(querry);
        //writeResults(resultSet);
        //saveProducts(resultSet);
        while (resultSet.next()) {
            String naam = resultSet.getString("Naam");
            double hoogte = resultSet.getDouble("Hoogte");
            double breedte = resultSet.getDouble("Breedte");
            int x = resultSet.getInt("X");
            int y = resultSet.getInt("Y");
            products.add(new Product(naam, hoogte, breedte, x, y));
        }
        return products;
        /*preparedStatement = connect.prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
        // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
        // Parameters start with 1
        preparedStatement.setString(1, "Test");
        preparedStatement.setString(2, "TestEmail");
        preparedStatement.setString(3, "TestWebpage");
        preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
        preparedStatement.setString(5, "TestSummary");
        preparedStatement.setString(6, "TestComment");
        preparedStatement.executeUpdate();*/
        /*preparedStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
        resultSet = preparedStatement.executeQuery();
        writeResultSet(resultSet);*/
        /*preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
        preparedStatement.setString(1, "Test");
        preparedStatement.executeUpdate();*/
    }

    static public void ConnAddOrder(int userId, ArrayList<int[]> products) throws SQLException {

        PreparedStatement bestellingStmt = connect.prepareStatement(
                "insert into Bestelling (GebruikerId) values (?);"
        );
        bestellingStmt.setInt(1, userId);
        bestellingStmt.executeUpdate();

        Statement lastIdStmt = connect.createStatement();
        ResultSet lastIdRs = lastIdStmt.executeQuery("select last_insert_id() as last_id from Bestelling");
        lastIdRs.next();
        int orderId = lastIdRs.getInt("last_id");

        PreparedStatement productStmt = connect.prepareStatement(
                "insert into BestelRegel (BestellingId, ProductId, Aantal) values (?, ?, ?);"
        );
        for (int[] item : products) {
            productStmt.setInt(1, orderId);
            productStmt.setInt(2, item[0]);
            productStmt.setInt(3, item[1]);
            productStmt.executeUpdate();
        }
    }

    static private void writeResults(ResultSet resultSet) throws SQLException {
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        while (resultSet.next()) {
            String txt = "";
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                txt += resultSet.getMetaData().getColumnName(i) + " : " + resultSet.getString(i) + "; ";
            }
            System.out.println(txt);
        }
    }

    static public ArrayList<Product> getProducts() {
        return products;
    }

    static public void closeConn() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
