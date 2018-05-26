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
        // hiermee starten we de verbinding tussen de applicatie en de database
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://rene-home.myddns.me/KBS2A?user=JavaUsr&password=javakbs2a");
    }

    static public ArrayList<Product> ConnGetProducts() throws SQLException {
        // deze method zorgt ervoor dat alle producten uit de database worden opgehaald die ergens in het rek zijn opgeslagen
        statement = connect.createStatement();
        String querry = "select Naam, Hoogte, Breedte, X, Y from ProductOpslag JOIN Product ON ProductOpslag.ProductId = Product.ProductId;";
        resultSet = statement.executeQuery(querry);
        while (resultSet.next()) {
            // dit pakt iedere regel die word terug gegeen door de statement en converteerd het naar een Product
            String naam = resultSet.getString("Naam");
            double hoogte = resultSet.getDouble("Hoogte");
            double breedte = resultSet.getDouble("Breedte");
            int x = resultSet.getInt("X");
            int y = resultSet.getInt("Y");
            products.add(new Product(naam, hoogte, breedte, x, y));
        }
        return products;
    }

    static public ArrayList<Product> ConnGetProducts(int[] products, int[] count) throws SQLException {
        // deze method haalt de producten uit de bestelling op uit de database
        // wet hebben aleen de nummers nodig die geschijden zijn met commas
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
            // per regel maakt hij er een count[i] aantal producten van
            for (int c = 0; c < count[i]; ++c) {
                // for ieder count[i] aan producten plakken we die in het lijstje
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
        // dit is voornamelijk bedoeld als testing maar dit pakt de resultSet van de query en System.out het naar de console
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
        // dit sluit de connectie
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
            System.out.println(e.getMessage());
        }
    }
}
