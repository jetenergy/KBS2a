package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Random;

public class TspSimulatedAnnealing {

    static ArrayList<Product> besteTour;

    static private void nieuwTour(ArrayList<Product> huidigeTour, boolean laatzien) {
        int oudeAfstand = 999999;
        int afstand = 0;
        int lengte;

        //Maakt leeg product aan voor startpositie 0, 0.
        Product beginPunt = new Product("", 0, 0, -1, 0);

        Random r = new Random();

        ArrayList<Product> nieuweTour = new ArrayList<>(huidigeTour);

        //Kiest twee willekeurige producten.
        int p1 = r.nextInt(nieuweTour.size()-1);
        int p2 = r.nextInt(nieuweTour.size()-1);

        //Als de twee willekeurige producten hetzelfde zijn, genereer een nieuw product.
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

        // Een nieuwe array wordt gebruikt om de gehutselde producten in op te slaan.
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

        //Berekent de totale afstand van de route, als de nieuwe route sneller is dan de oude, wordt deze vervangen.
        for (int i = 1; i < nieuweTour.size(); i++) {
                lengte = nieuweTour.get(i - 1).abs(nieuweTour.get(i));
                afstand = afstand + lengte;
            }

                if(afstand < oudeAfstand){
                    oudeAfstand = afstand;
                    nieuweTour.remove(0);
                    besteTour = new ArrayList<>(nieuweTour);
                    // TODO: functie visualiseren beste oplossing
                    if(laatzien){

            }
        } else {
            nieuweTour.remove(0);

        }
        // TODO: functie visualiseren huidige try
        if(laatzien){

        }
    }

    public static ArrayList<Product> SimulatedAnnealing (ArrayList<Product> producten, boolean laatzien) {
        ArrayList<Product> huidigeTour = new ArrayList<Product>(producten);
        int temperatuur = 1000;
        int afkoeling = 1;

        //Temperatuur gedeeld door afkoeling is het totaal aantal iteraties. Hoe lager de temperatuur, hoe beter de oplossing.
        while (temperatuur != 0) {
                nieuwTour(huidigeTour, laatzien);
                temperatuur -= afkoeling;

            }
        System.out.println("DONE -- SIMULATED ANNEALING");
        return besteTour;
}

    public static ArrayList<Product> SimulatedAnnealing (ArrayList<Product> producten){
        return SimulatedAnnealing(producten,false);
    }
}

