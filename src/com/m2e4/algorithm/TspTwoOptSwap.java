package com.m2e4.algorithm;

import java.util.ArrayList;

public class TspTwoOptSwap {

    private static final int iteraties = 50;

    public static void TwoOptSwap(ArrayList<Product> products) {
        int bestDistance = 999999;
        ArrayList<Product> huidigeBest = new ArrayList<>();
        for (int x = 0; x < iteraties; x++) {
            for (int i = 0; i < products.size(); i++) {
                for (int k = i+1; k < products.size(); k++) {
                    //wissel function
                    ArrayList<Product> dezeIteratie = new ArrayList<>();
                    for(int c = 0; c < i-1; c++){
                        dezeIteratie.add(products.get(c));
                    }

                    for(int c = k; c > i; c--){
                        dezeIteratie.add(products.get(c));
                    }

                    for(int c = k+1; c < products.size(); c++){
                        dezeIteratie.add(products.get(c));
                    }
                    // bereken de totaal afstand
                    int totalDistance = 0;
                    for(int z = 1; z < dezeIteratie.size(); z++){
                        totalDistance += dezeIteratie.get(z-1).abs(dezeIteratie.get(z));
                    }

                }
            }
        }
    }

    public int distance(int[] array){
        int totalDistance = 0;
        for(int x = 1; x < array.length; x++){
                totalDistance = Math.abs(array[x-1] - array[x]);
        }
        return totalDistance;
    }
}
