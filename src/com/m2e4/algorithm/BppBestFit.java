package com.m2e4.algorithm;

import java.util.ArrayList;

public class BppBestFit extends BppAlgorithm {

    private boolean ran = false;

    public BppBestFit(int boxCount, double boxSize) {
        super(boxCount, boxSize);
    }

    @Override
    public void run() {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        // Filling solution list with boxes
        solution = new ArrayList<>();
        for (int i = 0; i < boxCount; ++i)
            solution.add(new Box(boxSize));

        // For each item...
        for (Item item : items) {
            // Checking each box to find the tightest fit
            Box bestBox = null;

            for (Box box : solution) {
                // Picking boxes with enough space to hold the item, then checking if the used space is
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

            // Adding the item to the box
            bestBox.add(item);
        }

        ran = true;
    }

}
