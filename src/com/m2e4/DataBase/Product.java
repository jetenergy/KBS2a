package com.m2e4.DataBase;

public class Product implements Comparable<Product> {
    private final String naam;
    private final double hoogte;
    private final double breedte;
    private int x;
    private int y;

    public Product(String naam, double hoogte, double breedte, int x, int y) {
        this.naam = naam;
        this.hoogte = hoogte;
        this.breedte = breedte;
        this.x = x;
        this.y = y;
    }
    public Product(double hoogte) {
        this("", hoogte, 0.0, 0, 0);
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

    @Override
    public int compareTo(Product product) {
        return Double.compare(this.hoogte, product.hoogte);
    }
}
