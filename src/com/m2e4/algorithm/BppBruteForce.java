package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class BppBruteForce implements Algorithm {

    private final ArrayList<Box> boxes = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Box> bestSolution;
    private short bestBoxesUsed = 32767;

    private int boxCount;
    private int boxSize;

    private boolean ran = false;

    public BppBruteForce(int boxCount, int boxSize) {
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

        for (int i = 0; i < boxCount; ++i)
            boxes.add(new Box(boxSize));

        tryItem(0);
        ran = true;
    }

    private void tryItem(int i) {
        boolean last = (i == items.size() - 1);

        for (Box b : boxes) {

            b.add(items.get(i));
            if (!(b.size > b.height)) {

                if (!last) tryItem(i + 1);
                else {
                    short boxesUsed = 0;

                    for (Box b1 : boxes)
                        if (b1.size != 0)
                            boxesUsed += 1;

                    if (boxesUsed < bestBoxesUsed) {
                        bestSolution = new ArrayList<>();
                        for (int i0 = 0; i0 < boxCount; ++i0)
                            bestSolution.add(new Box(boxSize));

                        for (int i1 = 0; i1 < boxes.size(); ++i1) {
                            for (int i2 = 0; i2 < boxes.get(i1).items.size(); ++i2) {
                                bestSolution.get(i1).items.add(
                                        boxes.get(i1).items.get(i2)
                                );
                            }
                        }

                        bestBoxesUsed = boxesUsed;
                    }
                }

            }
            b.remove(items.get(i));
        }

    }

    @Override
    public Object getSolution() {
        return bestSolution;
    }

    public static class Box {
        private final double height;
        private final ArrayList<Item> items = new ArrayList<>();
        private double size = 0.0;

        public Box(double height) {
            this.height = height;
        }
        public double getHeight() {
            return height;
        }
        public void add(Item i) {
            size += i.height;
            items.add(i);
        }
        public void remove(Item i) {
            size -= i.height;
            items.remove(i);
        }
        public ArrayList<Item> getItems() {
            return items;
        }
    }

    public static class Item {
        private final double height;

        public Item(double height) {
            this.height = height;
        }
        public double getHeight() {
            return height;
        }
    }
}
