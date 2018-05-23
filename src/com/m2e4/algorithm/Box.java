package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box {
    private final double height;
    private final ArrayList<Item> items = new ArrayList<>();
    private double usedHeight = 0.0; // Amount of space already used by Items

    public Box(double height) {
        this.height = height;
    }

    /**
     * Adds an Item to the box
     * Does not consider the box's maximum size
     * @param i An Item
     */
    public void add(Item i) {
        usedHeight += i.getHeight();
        items.add(i);
    }

    /**
     * Removes an Item from the box
     * @param i An Item
     */
    public void remove(Item i) {
        if (!items.contains(i)) return;
        usedHeight -= i.getHeight();
        items.remove(i);
    }

    public double getHeight() {
        return height;
    }

    public double getUsedHeight() {
        return this.usedHeight;
    }

    /**
     * Gets the items in the box
     * @return An immutable List of Items
     */
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}