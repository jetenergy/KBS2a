package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;

public class TspGreedy {

    public static ArrayList<Product> Greedy(ArrayList<Product> producten, boolean laatzien){
        double nieuweAfstand;

        ArrayList<Product> workingProducts = new ArrayList<>(producten);
        ArrayList<Product> huidigBest = new ArrayList<>();

        //Maakt leeg product aan voor startpositie 0, 0.
        Product laatste = new Product("", 0, 0, -1, 0);
        Product next;

        while(workingProducts.size() > 0){
            nieuweAfstand = 999999;
            Product bestNext = new Product("", 0, 0, 9999, 9999);

            // Gaat array langs om kortste route van huidige product naar product+1 te vinden.
            for (Product workingProduct : workingProducts) {
                next = workingProduct;
                double afstand = laatste.abs(next);

                //Als er nieuwe betere route is gevonden, wordt de oude vervangen met de nieuwe.
                if (nieuweAfstand > afstand) {
                    bestNext = next;
                    nieuweAfstand = afstand;

                }
            }
            // Voegt kortste route toe aan return array, verwijdert dat product uit de oorspronkelijke array.
            huidigBest.add(bestNext);
            laatste = bestNext;
            workingProducts.remove(bestNext);

            if(laatzien){
                //System.out.println(huidigBest);
            }
        }

        System.out.println("DONE -- GREEDY");
        return huidigBest;
    }

    public static ArrayList<Product> Greedy(ArrayList<Product> producten){
        return Greedy(producten, false);
    }

}
