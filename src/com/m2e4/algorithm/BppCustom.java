package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BppCustom implements Algorithm {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<ArrayList<Item>> solution;

    private int boxCount;
    private double boxSize;

    public BppCustom(int boxCount, double boxSize) {
        this.boxCount = boxCount;
        this.boxSize = boxSize;
    }

    public void setItems(Item[] i) {
        this.items = new ArrayList<>(Arrays.asList(i));
        Collections.sort(this.items);
    }

    @Override
    public void run() {
        ArrayList<ArrayList<Item>> branch = new ArrayList<>();
        ArrayList<Object> possibilities = new ArrayList<>();

        for (int i = 0; i < boxCount; ++i)
            branch.add(new ArrayList<>());

        tryItem(0, 0, branch, possibilities);
    }

    /**
     * Tries to add an item to the first possible box, starting with box startId
     * Recursively calls itself for each item
     * @param startId
     * @param branch
     */
    private void tryItem(int startId, int itemId, ArrayList<ArrayList<Item>> branch, ArrayList<Object> arraySet) {

    }

    @Override
    public Object getSolution() {
        return null;
    }



    public static class Item implements Comparable<Item> {
        private final double height;

        public Item(double height) {
            this.height = height;
        }
        public double getHeight() {
            return height;
        }

        @Override
        public int compareTo(Item o) {
            return Double.compare(this.height, o.height);
        }
    }
}
