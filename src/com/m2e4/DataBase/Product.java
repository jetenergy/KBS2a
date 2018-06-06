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
    public Product(String naam, double hoogte) {
        this(naam, hoogte, 0.0, 0, 0);
    }

    public Product(Product product) {
        this(product.getNaam(), product.getHoogte(), product.getBreedte(), product.getX(), product.getY());
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
        // hiermee kunnen we voor TSP precies bepalen hoe ver de afstand is tussen 2 punten
        int x = Math.abs(this.getX() - compare.getX());
        int y = Math.abs(this.getY() - compare.getY());
        return Math.sqrt(Math.exp(x)+Math.exp(y));
    }

    @Override
    public String toString() {
        // dit is voornamelijk voor test
        return String.format("Product: %s, X positie: %d, Y positie: %d", getNaam(), getX(), getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) return false;
        return this.naam.equals(((Product) obj).naam) &&
                this.hoogte == ((Product) obj).hoogte;
    }

    @Override
    public int compareTo(Product product) {
        return Double.compare(this.hoogte, product.hoogte);
    }

    public boolean compareXY(Product product) {
        return this.x == product.getX() && this.y == product.getY();
    }
}
