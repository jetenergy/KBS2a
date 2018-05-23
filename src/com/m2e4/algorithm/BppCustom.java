package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class BppCustom extends BppAlgorithm {

    private boolean ran = false;

    public BppCustom(int boxCount, double boxSize) {
        super(boxCount, boxSize);
    }

    @Override
    public void setItems(Item[] i) {
        // Sets the items and sorts the item list in descending order
        super.setItems(i);
        Collections.sort(this.items);
        Collections.reverse(this.items);
    }

    @Override
    public void run() {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        if (items.size() == 0)
            throw new RuntimeException("Must have at least one item");

        // Filling solution list with boxes
        solution = new ArrayList<>();
        for (int i = 0; i < boxCount; ++i) {
            solution.add(new Box(boxSize));
        }

        // Trying to fill each box if there are still items left
        for (int i = 0; i < boxCount; ++i) {
            if (items.size() != 0) {
                ArrayList<Item> result = tryFill(i);

                // Places the items in the box
                for (Item item : result) {
                    solution.get(i).add(item);
                }
            }
        }

        // If there are still items left, there was not enough space
        if (items.size() > 0) solution = null;
        ran = true;
    }

    /**
     * Attempts to fill a box with items
     * @param boxId The index for box to fill
     * @return A list of items that should be placed in the box
     */
    private ArrayList<Item> tryFill(int boxId) {
        ArrayList<Item> newBox = new ArrayList<>();
        // Always places one item in at first
        newBox.add(items.get(0));
        items.remove(0);
        tryItems(newBox, items);
        return newBox;
    }

    /**
     * Attempts to find the best fit of Items
     * @param current The current box's contents
     * @param itemList The items that are allowed to be fit
     */
    private void tryItems(ArrayList<Item> current, ArrayList<Item> itemList) {
        double spaceAvailable = 0.0;
        // Calculating the amount of space available in a box
        for (Item i : current) spaceAvailable += i.getHeight();
        spaceAvailable = boxSize - spaceAvailable;
        Item bestItem = null;
        double bestSpace = spaceAvailable;

        // For each possible item...
        for (Item i : itemList) {
            // Checking if the item will not fit
            if (spaceAvailable - i.getHeight() < 0) continue;
            // Checking if the item will fit perfectly
            if (spaceAvailable - i.getHeight() == 0) {
                current.add(i);
                itemList.remove(i);
                return;
            }

            // Checking if the space available would be less than with the best Item
            if (bestSpace > spaceAvailable - i.getHeight()) {
                bestItem = i;
                bestSpace = spaceAvailable - i.getHeight();
            }
        }

        // If no best item was found, there was not enough space
        if (bestItem == null) return;
        // Adding the best item to the box
        current.add(bestItem);
        itemList.remove(bestItem);
        // Keep trying
        tryItems(current, itemList);
    }

}
