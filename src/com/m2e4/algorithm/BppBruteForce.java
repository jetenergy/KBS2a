package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class BppBruteForce implements Algorithm {

    private final ArrayList<Box> boxes = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Box> bestSolution;
    private short bestBoxesUsed = 32767;

    private int boxCount;
    private double boxSize;

    private boolean ran = false;

    public BppBruteForce(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    public void setItems(Item[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
    }

    @Override
    public void run() {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        // Filling boxes list with boxes
        for (int i = 0; i < boxCount; ++i)
            boxes.add(new Box(boxSize));

        tryItem(0);
        ran = true;
    }

    private void tryItem(int i) {
        // True when the index points to the last Item in the items list
        boolean last = (i == items.size() - 1);

        // For each box...
        for (Box b : boxes) {

            // Adding the first item to the box
            b.add(items.get(i));
            // If the used space in the box does not exceed the box's height...
            if (!(b.getUsedHeight() > b.getHeight())) {

                // Recursively calling this function if this item is not the last
                if (!last) tryItem(i + 1);
                else {
                    short boxesUsed = 0;

                    // Getting the amount of used boxes
                    for (Box b1 : boxes)
                        if (b1.getUsedHeight() != 0)
                            boxesUsed += 1;

                    // If the amount of used boxes is smaller than the current best...
                    if (boxesUsed < bestBoxesUsed) {
                        // Set the best solution to be this solution
                        bestSolution = new ArrayList<>();
                        for (int i0 = 0; i0 < boxCount; ++i0)
                            bestSolution.add(new Box(boxSize));

                        for (int i1 = 0; i1 < boxes.size(); ++i1) {
                            for (int i2 = 0; i2 < boxes.get(i1).getItems().size(); ++i2) {
                                bestSolution.get(i1).add(
                                        boxes.get(i1).getItems().get(i2)
                                );
                            }
                        }

                        bestBoxesUsed = boxesUsed;
                    }
                }

            }
            // Removes the item from the box
            b.remove(items.get(i));
        }

    }

    @Override
    public Object getSolution() {
        return bestSolution;
    }

}
