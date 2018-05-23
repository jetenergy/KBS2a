package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BppNextFit implements Algorithm {

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<ArrayList<Item>> solution;

    private int boxCount;
    private double boxSize;

    public BppNextFit(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    @Override
    public void setItems(Item[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
        Collections.sort(this.items);
    }

    @Override
    public void run() {
        solution = new ArrayList<>();

        for (int i = 0; i < boxCount; ++i)
            solution.add(new ArrayList<>());

        for (ArrayList<Item> box : solution) {
            if (items.size() == 0) break;
            double spaceAvailable = boxSize;

            while (true) {
                if (items.get(0).getHeight() <= spaceAvailable) {
                    box.add(items.get(0));
                    spaceAvailable -= items.get(0).getHeight();
                    items.remove(0);
                } else break;
                if (items.size() == 0) break;
            }
        }

        if (items.size() > 0) solution = null;
    }

    @Override
    public Object getSolution() {
        return solution;
    }

}
