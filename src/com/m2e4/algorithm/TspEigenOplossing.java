package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Random;

public class TspEigenOplossing {

    static Product beginPunt = new Product("", 0, 0, -1, 0);

    static Random r = new Random();

    static ArrayList<Product> nieuweTour = new ArrayList<>();
    static ArrayList<Product> huidigeTour = new ArrayList<>();
    static ArrayList<Product> besteTour;

    static int willekeurigGetal = r.nextInt(100);


    static int oudeAfstand = 999999;
    static int temperatuur = 100;
    static int afkoeling = 1;
    static int iteraties = 50;

    static private void nieuwTour(ArrayList<Product> huidigeTour) {
        int afstand = 0;
        int lengte;

        nieuweTour = huidigeTour;

        int p1 = r.nextInt(nieuweTour.size()-1);
        int p2 = r.nextInt(nieuweTour.size()-1);
        while(p1 == p2){
            p1 = r.nextInt(nieuweTour.size()-1);
        }
        if(p2 < p1){
            int tijdelijk = p1;
            p1 = p2;
            p2 = tijdelijk;
        }

        ArrayList<Product> dezeIteratie = new ArrayList<>();
        ArrayList<Product> randomize = new ArrayList<>(nieuweTour);
        for(int c = 0; c <= p1-1; c++){
            dezeIteratie.add(randomize.get(c));
        }

        for(int c = p2; c >= p1; c--){
            dezeIteratie.add(randomize.get(c));
        }

        for(int c = p2+1; c < randomize.size(); c++){
            dezeIteratie.add(randomize.get(c));
        }
        nieuweTour = new ArrayList<>(dezeIteratie);

        nieuweTour.add(0, beginPunt);

        for (int i = 1; i < nieuweTour.size(); i++) {
            lengte = nieuweTour.get(i - 1).abs(nieuweTour.get(i));
            afstand = afstand + lengte;
        }
        if(afstand < oudeAfstand){
            //System.out.println(afstand + " -- " + nieuweTour);
            oudeAfstand = afstand;
            nieuweTour.remove(0);
            besteTour = new ArrayList<>(nieuweTour);
            // TODO: visualiseren beste oplossing
        } else {
            nieuweTour.remove(0);
        }

    }

    public static ArrayList<Product> EigenOplossing(ArrayList<Product> producten){
        huidigeTour = producten;

        while (temperatuur > 0) {
            nieuwTour(huidigeTour);
            temperatuur -= afkoeling;
            //TODO: visualiseren huidige try
            //System.out.println(temperatuur + "--" + huidigeTour);
        }
        int bestDistance = 999999;
        ArrayList<Product> huidigeBest = new ArrayList<>();
        for (int x = 0; x < iteraties; x++) {
            for (int i = 0; i < besteTour.size(); i++) {
                for (int k = i+1; k < besteTour.size(); k++) {
                    //wissel function
                    ArrayList<Product> dezeIteratie = new ArrayList<>();
                    for(int c = 0; c <= i-1; c++){
                        dezeIteratie.add(besteTour.get(c));
                    }

                    for(int c = k; c >= i; c--){
                        dezeIteratie.add(besteTour.get(c));
                    }

                    for(int c = k+1; c < besteTour.size(); c++){
                        dezeIteratie.add(besteTour.get(c));
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
            //TODO: visualiseren huidige try
        }

        System.out.println("DONE - EIGEN OPLOSSING");
        return besteTour;
    }
}
