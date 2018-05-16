package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TspSimulatedAnnealing {

    static Random r = new Random();

    static ArrayList<Product> nieuweTour = new ArrayList<>();
    static ArrayList<Product> huidigeTour = new ArrayList<>();

    static int willekeurigGetal = r.nextInt(100);
    static int willekeurigElement = r.nextInt();
    static int oudLengte = 0;
    static int nieuwLengte = 0;
    static int temperatuur = 100;
    static double afkoeling = 0.1;




    static private void nieuwTour(ArrayList<Product> producten) {
        Product product1 = huidigeTour.get(willekeurigGetal.huidigeTour.size());
        Product product2 = huidigeTour.get(r.nextInt(huidigeTour.size()));
        huidigeTour.remove(product1);
        huidigeTour.remove(product2);
        Collections.reverse(huidigeTour);
        huidigeTour.add(product1);
        huidigeTour.add(product2);

        nieuwLengte = oudLengte;
        huidigeTour = nieuweTour;
    }

        public static ArrayList<Product> SimulatedAnnealing (ArrayList<Product> producten) {
            Collections.shuffle(producten);
            huidigeTour = producten;

            for (int i = 1; i < producten.size(); i++) {
                oudLengte += producten.get(i - 1).abs(producten.get(i));
                System.out.println(oudLengte);
            }

            while (temperatuur != 0) {

                for (int i = 1; i < nieuweTour.size(); i++) {
                    nieuwLengte += nieuweTour.get(i - 1).abs(nieuweTour.get(i));
                }

                if (nieuwLengte < oudLengte) {
                    nieuwTour(huidigeTour);
                } else {
                    if (temperatuur >= 66) {
                        if (willekeurigGetal > 45) {
                            nieuwTour(huidigeTour);

                        }
                    } else if (temperatuur <= 33) {
                        if (willekeurigGetal < 10) {
                            nieuwTour(huidigeTour);
                        }
                    } else {
                        if (willekeurigGetal > 10 && willekeurigGetal < 45) {
                            nieuwTour(huidigeTour);
                        }
                    }
                }

                temperatuur -= afkoeling;
            }

            return nieuweTour;
        }


    }

