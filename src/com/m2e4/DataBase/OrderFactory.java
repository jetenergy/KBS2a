package com.m2e4.DataBase;

import com.google.gson.*;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFactory {

    /**
     * Uses a Reader to parse a JSON file and uses data from the database
     * @param reader The Reader to read from
     * @return The products that have been read from the Reader
     * @throws SQLException Is thrown when something goes wrong with the Database
     */
    public static ArrayList<Product> processJsonOrder(Reader reader) throws SQLException {
        JsonObject element = new JsonParser().parse(reader).getAsJsonObject();

        ArrayList<int[]> productsList = new ArrayList<>();

        // Reading products from JSON file
        JsonArray order = element.get("Order").getAsJsonArray();
        for (JsonElement x : order) {
            JsonObject item = x.getAsJsonObject();
            productsList.add(
                    new int[]{item.get("ProductId").getAsInt(), item.get("Aantal").getAsInt()}
            );
        }

        // Creating and filling arrays for use with the DataBase class
        int[] products = new int[productsList.size()];
        int[] count = new int[productsList.size()];

        for (int i = 0; i < productsList.size(); i++) {
            int[] p = productsList.get(i);
            products[i] = p[0];
            count[i] = p[1];
        }

        // Getting products from the Database
        return DataBase.ConnGetProducts(products, count);
    }

}
