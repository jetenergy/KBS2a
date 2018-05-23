package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BppCustom implements Algorithm {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Box> solution;

    private int boxCount;
    private double boxSize;

    private boolean ran = false;

    public BppCustom(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    public void setItems(Item[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
        Collections.sort(this.items);
        Collections.reverse(this.items);
    }

    @Override
    public void run() {
        if (ran)
            throw new RuntimeException("Cannot run algorithm again");

        if (items.size() == 0)
            throw new RuntimeException("Must have at least one item");

        // Create and fill solution list
        solution = new ArrayList<>();
        for (int i = 0; i < boxCount; ++i) {
            solution.add(new Box(boxSize));
        }


        for (int i = 0; i < boxCount; ++i) {
            if (items.size() != 0) {
                ArrayList<Item> result = tryFill(i);

                for (Item item : result) {
                    solution.get(i).add(item);
                }
            }
        }

        if (items.size() > 0) solution = null;
        ran = true;
    }

    private ArrayList<Item> tryFill(int boxId) {
        ArrayList<Item> newBox = new ArrayList<>();
        newBox.add(items.get(0));
        items.remove(0);
        tryItems(newBox, items);
        return newBox;
    }

    private void tryItems(ArrayList<Item> current, ArrayList<Item> itemList) {
        double spaceAvailable = 0.0;
        for (Item i : current) spaceAvailable += i.getHeight();
        spaceAvailable = boxSize - spaceAvailable;
        System.out.println(String.format("Space available: %f", spaceAvailable));
        Item bestItem = null;
        double bestSpace = spaceAvailable;

        for (Item i : itemList) {
            if (spaceAvailable - i.getHeight() < 0) continue;
            if (spaceAvailable - i.getHeight() == 0) {
                current.add(i);
                itemList.remove(i);
                return;
            }

            if (bestSpace > spaceAvailable - i.getHeight()) {
                bestItem = i;
                bestSpace = spaceAvailable - i.getHeight();
            }
        }

        if (bestItem == null) return;
        current.add(bestItem);
        itemList.remove(bestItem);
        tryItems(current, itemList);
    }

    @Override
    public Object getSolution() {
        return solution;
    }
}
