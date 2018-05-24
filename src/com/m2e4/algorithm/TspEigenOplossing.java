package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;

public class TspEigenOplossing {

    public static ArrayList<Product> EigenOplossing(ArrayList<Product> producten){
        return TspTwoOptSwap.TwoOptSwap(TspSimulatedAnnealing.SimulatedAnnealing(producten));
    }
}

