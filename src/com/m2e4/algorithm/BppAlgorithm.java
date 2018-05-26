package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BppAlgorithm {

    /** Lets an algorithm know it's being interrupted
     * Each algorithm handles this separately */
    protected boolean isInterrupted = false;

    ArrayList<Product> items = new ArrayList<>();
    ArrayList<Box> solution = new ArrayList<>();

    int boxCount;     // Amount of boxes available
    double boxSize;   // Height available for items

    public BppAlgorithm(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    public void setItems(Product[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
    };

    /**
     * Runs an algorithm
     * @throws InterruptedException Indicates that the algorithm has been interrupted
     */
    public abstract void run() throws InterruptedException;
    public Object getSolution() {
        return solution;
    }

    public void interrupt() {
        isInterrupted = true;
    }

}
