package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BppAlgorithm {

    ArrayList<Product> items = new ArrayList<>();
    ArrayList<Box> solution = new ArrayList<>();

    int boxCount;
    double boxSize;

    public BppAlgorithm(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    public void setItems(Product[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
    };
    public abstract void run();
    public Object getSolution() {
        return solution;
    }

}
