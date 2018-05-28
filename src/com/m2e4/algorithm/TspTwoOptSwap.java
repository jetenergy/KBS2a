package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;

public class TspTwoOptSwap {
    // dit begin punt word gebruikt om de beste route te berekenen
    private static Product beginPunt = new Product("", 0, 0, -1, 0);
    private static boolean interupt = false;

    public static ArrayList<Product> TwoOptSwap(ArrayList<Product> products) throws InterruptedException {
        interupt = false;
        ArrayList<Product> beste_route = new ArrayList<>(products);
        boolean check = true;
        // deze loop gaat door tot hij geen betere route kan vinden
        loop: while (check) {

            double best_distance = calculateTotalDistance(beste_route);
            double new_distance;

            for (int i = 0; i < beste_route.size() - 1; i++) {
                for (int k = i + 1; k < beste_route.size(); k++) {
                    if (interupt) {
                        throw new InterruptedException();
                    }
                    ArrayList<Product> nieuwe_route = swap(beste_route, i, k);
                    new_distance = calculateTotalDistance(nieuwe_route);
                    // deze check is er om te kijken of de nieuwe_route beter is dan de oude beste
                    if (new_distance < best_distance) {
                        beste_route = new ArrayList<>(nieuwe_route);
                        // als hij een betere route vind gaat hij weer terug naar het begin van de while loop
                        continue loop;
                    }
                }
            }
            // als hij geen beter route heeft gevonden komt hij door beide for loops heen en zet hij check op false
            // waardoor hij de beste_route gaat retourneren
            check = false;
        }
        return beste_route;
    }

    private static double calculateTotalDistance(ArrayList<Product> route) {
        // deze method pakt een route voegd tijdelijk het begin punt eraan toe en berekend hoelang de route is en returnd deze waarde
        double distance = 0;
        route.add(0, beginPunt);
        for (int i = 1; i < route.size(); i++) {
            distance += route.get(i - 1).abs(route.get(i));
        }
        route.remove(beginPunt);
        return distance;
    }

    private static ArrayList<Product> swap(ArrayList<Product> route, int i, int k) {
        // swap zorgt ervoor dat de route vanaf 0 tot aan i-1 word toegevoegd aan de nieuwe route
        // daarna word punt k tot en met i gepakt (van achter naar vooren) en plakt deze aan de nieuwe route
        // als laatste word punt k+1 tot aan het einde van de route weer achter de nieuwe route geplakt
        ArrayList<Product> nieuweRoute = new ArrayList<>();
        for(int c = 0; c <= i-1; c++){
            nieuweRoute.add(route.get(c));
        }

        for(int c = k; c >= i; c--){
            nieuweRoute.add(route.get(c));
        }

        for(int c = k+1; c < route.size(); c++){
            nieuweRoute.add(route.get(c));
        }
        return nieuweRoute;
    }

    public static void stop() {
        interupt = true;
    }
}
