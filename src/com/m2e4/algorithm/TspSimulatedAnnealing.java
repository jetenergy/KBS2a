package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Collections;

public class TspSimulatedAnnealing {
    public static ArrayList<Product> SimulatedAnnealing(ArrayList<Product> products) {
        ArrayList<Product> producten = new ArrayList<>(products);

        double temp = 100000;
        double coolingRate = 0.003;

        ArrayList<Product> currentSolution = new ArrayList<>(producten);
        Collections.shuffle(currentSolution);
        ArrayList<Product> best = new ArrayList<>(currentSolution);

        ArrayList<Product> newSolution;

        while (temp > 1) {
            newSolution = new ArrayList<>(currentSolution);

            int tourPos1 = (int) (newSolution.size() * Math.random());
            int tourPos2 = (int) (newSolution.size() * Math.random());

            Product citySwap1 = newSolution.get(tourPos1);
            Product citySwap2 = newSolution.get(tourPos2);

            newSolution.set(tourPos2, citySwap1);
            newSolution.set(tourPos1, citySwap2);

            double currentEnergy = getDistance(currentSolution);
            double neighbourEnergy = getDistance(newSolution);

            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new ArrayList<>(newSolution);
            }

            if (getDistance(currentSolution) < getDistance(best)) {
                best = new ArrayList<>(currentSolution);
            }
            temp *= 1-coolingRate;
        }
        return best;
    }

    private static Product begin = new Product("", 0, 0, -1, 0);
    private static Product eind = new Product("", 0, 0, -1, 0);
    private static double getDistance(ArrayList<Product> products){
        double tourDistance = 0;
        products.add(0, begin);
        products.add(eind);
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
        products.remove(begin);
        products.remove(eind);
        return tourDistance;
    }

    private static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
}