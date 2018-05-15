package com.m2e4.DataBase;

public class Product {
    private String naam;
    private double hoogte;
    private double breedte;
    private int x;
    private int y;

    public Product() {

    }

    Product(String naam, double hoogte, double breedte, int x, int y) {
        this.naam = naam;
        this.hoogte = hoogte;
        this.breedte = breedte;
        this.x = x;
        this.y = y;
    }

    public String getNaam() {
        return naam;
    }

    public double getHoogte() {
        return hoogte;
    }

    public double getBreedte() {
        return breedte;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int abs(Product compare){
        int x = Math.abs(this.getX() - compare.getX());
        int y = Math.abs(this.getY() - compare.getY());
        return x+y;
    }

    @Override
    public String toString() {
        return getNaam() + ", " + getHoogte() + ", " + getBreedte() + ", " + getX() + ", " + getY() + ";";
    }
}
