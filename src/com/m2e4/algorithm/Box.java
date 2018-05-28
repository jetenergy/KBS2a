package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box {
    private final double height;
    private final ArrayList<Product> items;
    private double usedHeight = 0.0; // Amount of space already used by Items

    public Box(double height) {
        this.height = height;
        items = new ArrayList<>();
    }
    public Box(Box box) {
        this.height = box.height;
        items = new ArrayList<>(box.getItems());
    }

    /**
     * Adds an Item to the box
     * Does not consider the box's maximum size
     * @param i An Item
     */
    public void add(Product i) {
        usedHeight += i.getHoogte();
        items.add(i);
    }

    /**
     * Removes an Item from the box
     * @param i An Item
     */
    public void remove(Product i) {
        if (!items.contains(i)) return;
        usedHeight -= i.getHoogte();
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
    public List<Product> getItems() {
        return Collections.unmodifiableList(items);
    }
}