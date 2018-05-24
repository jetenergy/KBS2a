package com.m2e4.DataBase;

public class Product {
    private String naam;
    private double hoogte;
    private double breedte;
    private int x;
    private int y;

    public Product() {

    }

    public Product(String naam, double hoogte, double breedte, int x, int y) {
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double abs(Product compare){
        int x = Math.abs(this.getX() - compare.getX());
        int y = Math.abs(this.getY() - compare.getY());
        return Math.sqrt(Math.exp(x)+Math.exp(y));
    }

    @Override
    public String toString() {
        return getNaam() + ", " + getHoogte() + ", " + getBreedte() + ", " + getX() + ", " + getY() + ";";
    }
}
