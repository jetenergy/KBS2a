package com.m2e4.DataBase;

import com.google.gson.*;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFactory {

    private static Gson gson = new Gson();

    /**
     * Uses a Reader to parse a JSON file and places the data into the database
     * @param reader The reader to read from
     */
    public static void processJsonOrder(Reader reader) throws SQLException, NumberFormatException {
        JsonObject element = new JsonParser().parse(reader).getAsJsonObject();

        int userId;
        ArrayList<int[]> products = new ArrayList<>();

        userId = element.get("GebruikerId").getAsInt();
        JsonArray order = element.get("Order").getAsJsonArray();
        for (JsonElement x : order) {
            JsonObject item = x.getAsJsonObject();
            products.add(
                    new int[]{item.get("ProductId").getAsInt(), item.get("Aantal").getAsInt()}
            );
        }

        DataBase.ConnAddOrder(userId, products);
    }

}
