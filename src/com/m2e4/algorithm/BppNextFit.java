package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class BppNextFit extends BppAlgorithm {

    private boolean ran;

    public BppNextFit(int boxCount, double boxSize) {
        super(boxCount, boxSize);
    }

    @Override
    public void setItems(Item[] i) {
        // Sets the items and sorts the item list in ascending order
        super.setItems(i);
        Collections.sort(this.items);
    }

    @Override
    public void run() {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        // Filling solution list with boxes
        solution = new ArrayList<>();
        for (int i = 0; i < boxCount; ++i)
            solution.add(new Box(boxSize));

        // For each box...
        for (Box box : solution) {
            // Stops if there are no more items to fit
            if (items.size() == 0) break;

            // Continually attempts to fit items into the box
            while (true) {
                // Adds the item if it fits, stops checking if the item does not fit
                if (items.get(0).getHeight() <= box.getHeight() - box.getUsedHeight()) {
                    box.add(items.get(0));
                    items.remove(0);
                } else break;
                // Stops checking if there are no more items to fit
                if (items.size() == 0) break;
            }
        }

        // If there are still items left, there was not enough space
        if (items.size() > 0) solution = null;
        ran = true;
    }

}
