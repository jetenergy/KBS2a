package com.m2e4.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box {
    private final double height;
    private final ArrayList<Item> items = new ArrayList<>();
    private double usedHeight = 0.0;

    public Box(double height) {
        this.height = height;
    }

    public void add(Item i) {
        usedHeight += i.getHeight();
        items.add(i);
    }
    public void remove(Item i) {
        usedHeight -= i.getHeight();
        items.remove(i);
    }

    public double getHeight() {
        return height;
    }

    public double getUsedHeight() {
        return this.usedHeight;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}