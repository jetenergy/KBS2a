package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;


public class TspTwoOptSwap {

    private static Product beginPunt = new Product("", 0, 0, -1, 0);

    public static ArrayList<Product> TwoOptSwap(ArrayList<Product> products) {
        ArrayList<Product> workingProducts = new ArrayList<>(products);
        boolean check = true;
        hank: while (check) {

            double best_distance = calculateTotalDistance(workingProducts);
            double new_distance;

            for (int i = 0; i < workingProducts.size() - 1; i++) {
                for (int k = i + 1; k < workingProducts.size(); k++) {
                    ArrayList<Product> new_route = swap(workingProducts, i, k);
                    new_distance = calculateTotalDistance(new_route);
                    if (new_distance < best_distance) {
                        workingProducts = new ArrayList<>(new_route);
                        continue hank;
                    }
                }
            }
            check = false;
        }
        return workingProducts;
    }

    private static double calculateTotalDistance(ArrayList<Product> lijst) {
        double distance = 0;
        lijst.add(0, beginPunt);
        for (int i = 1; i < lijst.size(); i++) {
            distance += lijst.get(i - 1).abs(lijst.get(i));
        }
        lijst.remove(beginPunt);
        return distance;
    }

    private static ArrayList<Product> swap(ArrayList<Product> route, int i, int k) {
        ArrayList<Product> product = new ArrayList<>();
        for(int c = 0; c <= i-1; c++){
            product.add(route.get(c));
        }

        for(int c = k; c >= i; c--){
            product.add(route.get(c));
        }

        for(int c = k+1; c < route.size(); c++){
            product.add(route.get(c));
        }
        return product;
    }

}
