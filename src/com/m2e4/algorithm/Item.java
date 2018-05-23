package com.m2e4.algorithm;

public class Item implements Comparable<Item> {
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
