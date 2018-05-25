package com.m2e4.DataBase;

import com.google.gson.*;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFactory {

    /**
     * Uses a Reader to parse a JSON file and places the data into the database
     * @param reader The reader to read from
     */
    public static ArrayList<Product> processJsonOrder(Reader reader) throws SQLException, NumberFormatException {
        JsonObject element = new JsonParser().parse(reader).getAsJsonObject();

        int userId;
        ArrayList<int[]> productsList = new ArrayList<>();

        JsonArray order = element.get("Order").getAsJsonArray();
        for (JsonElement x : order) {
            JsonObject item = x.getAsJsonObject();
            productsList.add(
                    new int[]{item.get("ProductId").getAsInt(), item.get("Aantal").getAsInt()}
            );
        }

        Object[] products = new Object[productsList.size()];
        int[] count = new int[productsList.size()];

        for (int i = 0; i < productsList.size(); i++) {
            int[] p = productsList.get(i);
            products[i] = p[0];
            count[i] = p[1];
        }

        return DataBase.ConnGetProducts(products, count);
    }

}
