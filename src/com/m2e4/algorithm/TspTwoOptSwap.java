package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Collections;


public class TspTwoOptSwap {

    private static final int iteraties = 50;

    public static ArrayList<Product> TwoOptSwap(ArrayList<Product> products) {
        int bestDistance = 999999;
        ArrayList<Product> huidigeBest = new ArrayList<>();
        ArrayList<Product> tijdelijkProducten = new ArrayList<>(products);
        for (int x = 0; x < iteraties; x++) {
            Collections.shuffle(tijdelijkProducten);
            for (int i = 0; i < tijdelijkProducten.size(); i++) {
                for (int k = i+1; k < tijdelijkProducten.size(); k++) {
                    //wissel function
                    ArrayList<Product> dezeIteratie = new ArrayList<>();
                    for(int c = 0; c <= i-1; c++){
                        dezeIteratie.add(tijdelijkProducten.get(c));
                    }

                    for(int c = k; c >= i; c--){
                        dezeIteratie.add(tijdelijkProducten.get(c));
                    }

                    for(int c = k+1; c < tijdelijkProducten.size(); c++){
                        dezeIteratie.add(tijdelijkProducten.get(c));
                    }
                    // bereken de totaal afstand
                    int totalDistance = 0;
                    for(int z = 1; z < dezeIteratie.size(); z++){
                        totalDistance += dezeIteratie.get(z-1).abs(dezeIteratie.get(z));
                    }
                    if (totalDistance < bestDistance) {
                        bestDistance = totalDistance;
                        huidigeBest = dezeIteratie;
                        // TODO: visualiseren beste oplossing
                    }
                }
            }
        // TODO: visualiseren huidige try
        }
        System.out.println("DONE -- TWO OPT");
        return huidigeBest;
    }

    /*public int distance(int[] array){
        int totalDistance = 0;
        for(int x = 1; x < array.length; x++){
                totalDistance = Math.abs(array[x-1] - array[x]);
        }
        return totalDistance;
    }*/
}
