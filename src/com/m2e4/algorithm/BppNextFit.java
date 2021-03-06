package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Collections;

public class BppNextFit extends BppAlgorithm {

    private boolean ran;

    public BppNextFit(int boxCount, double boxSize) {
        super(boxCount, boxSize);
    }

    @Override
    public void setItems(Product[] i) {
        // Sets the items and sorts the item list in ascending order
        super.setItems(i);
        Collections.sort(this.items);
    }

    @Override
    public void run() throws InterruptedException {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        if (items.size() == 0)
            throw new RuntimeException("Must have at least one item");

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
                if (isInterrupted)
                    throw new InterruptedException();

                // Adds the item if it fits, stops checking if the item does not fit
                if (items.get(0).getHoogte() <= box.getHeight() - box.getUsedHeight()) {
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
