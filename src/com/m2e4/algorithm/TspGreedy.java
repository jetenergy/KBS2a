package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;

public class TspGreedy {

    public static ArrayList<Product> Greedy(ArrayList<Product> producten){
        int huidigeAfstand = 999999;
        int nieuweAfstand;
        ArrayList<Product> workingProducts = new ArrayList<>(producten);

        ArrayList<Product> huidigBest = new ArrayList<>();
        Product laatste = new Product("", 0, 0, -1, 0);
        Product next;
        while(workingProducts.size() > 0){
            nieuweAfstand = 999999;
            Product bestNext = new Product("", 0, 0, 9999, 9999);
            for(int i = 0; i < workingProducts.size(); i++) {
                next = workingProducts.get(i);
                int afstand = laatste.abs(next);
                if (nieuweAfstand > afstand) {
                    bestNext = next;
                    nieuweAfstand = afstand;

                }
            }
            huidigBest.add(bestNext);
            laatste = bestNext;
            workingProducts.remove(bestNext);
            // TODO: functie visualiseren oplossing
        }
        System.out.println("DONE -- GREEDY");
        return huidigBest;
    }

}
