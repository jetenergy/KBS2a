package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;

public class TspGreedy {
    private static boolean interupt = false;

    public static ArrayList<Product> Greedy(ArrayList<Product> producten) throws InterruptedException{
        interupt = false;
        double nieuweAfstand;

        ArrayList<Product> workingProducts = new ArrayList<>(producten);
        ArrayList<Product> finalRoute = new ArrayList<>();

        // maakt leeg product aan voor startpositie -1, 0.
        Product laatste = new Product("", 0, 0, -1, 0);
        Product next;

        Product bestNext = new Product("", 0, 0, 9999, 9999);

        // per product bekijken we welke het dichst bij het laatste product zit
        while(workingProducts.size() > 0){
            nieuweAfstand = 999999;

            // gaat array langs om kortste route van huidige product naar product+1 te vinden.
            for (Product workingProduct : workingProducts) {
                if (interupt) {
                    throw new InterruptedException();
                }

                next = workingProduct;
                double afstand = laatste.abs(next);

                // als er nieuwe betere route is gevonden, wordt de oude vervangen met de nieuwe.
                if (nieuweAfstand > afstand) {
                    bestNext = next;
                    nieuweAfstand = afstand;

                }
            }
            // voegt dichtsbijzinde product toe aan finalRoute, verwijdert dat product uit de oorspronkelijke array.
            finalRoute.add(bestNext);
            laatste = bestNext;
            workingProducts.remove(bestNext);
        }
        return finalRoute;
    }

    public static void stop() {
        interupt = true;
    }
}
