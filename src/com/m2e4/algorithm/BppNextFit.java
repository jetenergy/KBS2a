package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BppNextFit implements Algorithm {

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Box> solution;

    private int boxCount;
    private double boxSize;

    private boolean ran;

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
            solution.add(new Box(boxSize));

        for (Box box : solution) {
            if (items.size() == 0) break;

            while (true) {
                if (items.get(0).getHeight() <= box.getHeight() - box.getUsedHeight()) {
                    box.add(items.get(0));
                    items.remove(0);
                } else break;
                if (items.size() == 0) break;
            }
        }

        if (items.size() > 0) solution = null;
        ran = true;
    }

    @Override
    public Object getSolution() {
        return solution;
    }

}
