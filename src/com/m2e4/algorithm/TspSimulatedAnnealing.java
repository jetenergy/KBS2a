package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Collections;

public class TspSimulatedAnnealing {
    private static boolean interupt = false;

    public static ArrayList<Product> SimulatedAnnealing(ArrayList<Product> products) throws InterruptedException {
        interupt = false;
        ArrayList<Product> producten = new ArrayList<>(products);

        double temp = 100000;
        double coolingRate = 0.003;

        ArrayList<Product> currentSolution = new ArrayList<>(producten);
        // dit maakt een willekeurige oplossing van de input producten
        Collections.shuffle(currentSolution);
        // de currentSolution is ook gelijk de beste oplossing
        ArrayList<Product> best = new ArrayList<>(currentSolution);

        ArrayList<Product> newSolution;

        while (temp > 1) {
            if (interupt) {
                throw new InterruptedException();
            }
            // copieer de currentSolution naar newSolution zodat we daarmee kunnen werken
            newSolution = new ArrayList<>(currentSolution);

            // pak 2 willekeurige posities uit de gehele arrayList
            int tourPos1 = (int) (newSolution.size() * Math.random());
            int tourPos2 = (int) (newSolution.size() * Math.random());

            // pak de 2 willekeurige
            Product productSwap1 = newSolution.get(tourPos1);
            Product productSwap2 = newSolution.get(tourPos2);

            // verwissel de 2 producten
            newSolution.set(tourPos2, productSwap1);
            newSolution.set(tourPos1, productSwap2);

            double currentEnergy = getDistance(currentSolution);
            double neighbourEnergy = getDistance(newSolution);

            // bereken of we de nieuwe route gaan aannemen of niet
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new ArrayList<>(newSolution);
            }

            // als de afstand beter is dan de afstand van de beste accepteren we deze als de beste
            if (getDistance(currentSolution) < getDistance(best)) {
                best = new ArrayList<>(currentSolution);
            }
            temp *= 1-coolingRate;
        }
        return best;
    }

    private static Product begin = new Product("", 0, 0, -1, 0);
    private static double getDistance(ArrayList<Product> products){
        double tourDistance = 0;
        // voeg begin tijdelijk toe om de volledige lengte vanaf begin tot eind goed te kunnen berekenen
        products.add(0, begin);
        // reken uit vanaf element 0 (begin) tot het einde en weer element 0 (het einde) wat de totale lengte is
        for (int i=0; i < products.size(); i++) {
            Product fromProduct = products.get(i);
            Product destinationProduct;
            if(i+1 < products.size()){
                destinationProduct = products.get(i+1);
            }
            else{
                destinationProduct = products.get(0);
            }
            tourDistance += fromProduct.abs(destinationProduct);
        }
        // verwijder het begin punt weer
        products.remove(begin);
        return tourDistance;
    }

    private static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // als de nieuwe oplossing beter is selecteer dit
        if (newEnergy < energy) {
            return 1.0;
        }
        // als de nieuwe oplossing slechter is bereken de acceptatie waarschijnlijkheid
        return Math.exp((energy - newEnergy) / temperature);
    }

    public static void stop() {
        interupt = true;
    }
}