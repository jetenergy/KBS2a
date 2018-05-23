package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class BppBestFit implements Algorithm {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Box> solution;

    private int boxCount;
    private double boxSize;

    private boolean ran = false;

    public BppBestFit(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    @Override
    public void setItems(Item[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
    }

    @Override
    public void run() {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        // Fill solution list with boxes
        solution = new ArrayList<>();
        for (int i = 0; i < boxCount; ++i)
            solution.add(new Box(boxSize));

        // For each item...
        for (Item item : items) {
            // Check each box to find the tightest fit
            Box bestBox = null;

            for (Box box : solution) {
                // Picks boxes with enough space to hold the item, then checks if the used space is
                // less than the best box found
                if ( box.getHeight() - box.getUsedHeight() - item.getHeight() >= 0 ) {
                    if (bestBox == null || bestBox.getUsedHeight() < box.getUsedHeight())
                        bestBox = box;
                }
            }

            // Stops running if there is not enough space
            if (bestBox == null) {
                solution = null;
                return;
            }

            // Adds the item to the box
            bestBox.add(item);
        }

        ran = true;
    }

    @Override
    public Object getSolution() {
        return solution;
    }
}
