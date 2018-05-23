package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Algorithm {

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Box> solution = new ArrayList<>();

    int boxCount;
    double boxSize;

    public Algorithm(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    public void setItems(Item[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
    };
    public abstract void run();
    public Object getSolution() {
        return solution;
    }

}
