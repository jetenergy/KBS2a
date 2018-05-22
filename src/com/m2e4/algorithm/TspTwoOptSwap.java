package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Collections;


public class TspTwoOptSwap {

    public static ArrayList<Product> TwoOptSwap(ArrayList<Product> products, boolean laatzien) {

        //Iteraties worden gebruikt om het accurater te maken. Meer producten hebben meer iteraties nodig voor een goed resultaat.
        final int iteraties = 50;
        int bestDistance = 999999;

        ArrayList<Product> huidigeBest = new ArrayList<>();
        ArrayList<Product> tijdelijkProducten = new ArrayList<>(products);

        for (int x = 0; x < iteraties; x++) {

            Collections.shuffle(tijdelijkProducten);

            // Wisselfunctie
            for (int i = 0; i < tijdelijkProducten.size(); i++) {
                for (int k = i+1; k < tijdelijkProducten.size(); k++) {
                    // Een nieuwe array wordt gebruikt om de gehutselde producten in op te slaan.
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
                    // Bereken de totale afstand.
                    int totalDistance = 0;

                    for(int z = 1; z < dezeIteratie.size(); z++){
                        totalDistance += dezeIteratie.get(z-1).abs(dezeIteratie.get(z));
                    }

                    // Vervang de oude afstand als een kleinere afstand is gevonden. De array met de kortere afstand wordt de huidige array.
                    if (totalDistance < bestDistance) {
                        bestDistance = totalDistance;
                        huidigeBest = dezeIteratie;
                        // TODO: visualiseren beste oplossing
                        if(laatzien){

                        }
                    }
                }
            }
        // TODO: visualiseren huidige try
            if(laatzien){

            }
        }
        System.out.println("DONE -- TWO OPT");
        return huidigeBest;
    }

    public static ArrayList<Product> TwoOptSwap(ArrayList<Product> products){
        return TwoOptSwap(products, false);
    }

}
