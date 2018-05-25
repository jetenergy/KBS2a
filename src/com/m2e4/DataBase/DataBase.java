package com.m2e4.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    static public ArrayList<Product> ConnGetProducts(int[] products, int[] count) throws SQLException {

        String arr = Arrays.toString(products);
        arr = arr.substring(1, arr.length() - 1);

        PreparedStatement productStmt = connect.prepareStatement(
                String.format("select P.ProductId, Naam, Hoogte, Breedte, X, Y from ProductOpslag PO join Product P on PO.ProductId = P.ProductId where P.ProductId in (%s) order by P.ProductId;",
                        arr)
        );
        ResultSet productRs = productStmt.executeQuery();

        ArrayList<Product> prodOutput = new ArrayList<>();

        int i = 0;
        while (productRs.next()) {
            for (int c = 0; c < count[i]; ++c) {
                prodOutput.add(new Product(
                        productRs.getString("Naam"),
                        productRs.getDouble("Hoogte"),
                        productRs.getDouble("Breedte"),
                        productRs.getInt("X"),
                        productRs.getInt("Y")
                ));
            }
            ++i;
        }

        return prodOutput;

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
