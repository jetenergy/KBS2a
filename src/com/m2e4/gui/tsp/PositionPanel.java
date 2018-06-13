package com.m2e4.gui.tsp;

import com.m2e4.DataBase.Product;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class PositionPanel extends JPanel{

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private ArrayList<Product> producten = new ArrayList<>();
    private int gridHeight = 5;
    private int gridWidth = 5;
    private int squareSize = 50 * 5;
    private boolean isLive;
    private Product currentPosition = new Product("", 0, 0, 999, 999);

    public PositionPanel(String label, boolean isLive) {
        // maak een paneel met de titel label
        setBorder(border);
        JLabel title = new JLabel(label);
        add(title);
        this.isLive = isLive;
    }

    public PositionPanel(String label) {
        this(label, false);
    }

    public void setProducten(ArrayList<Product> producten) {
        this.producten = new ArrayList<>(producten);
    }

    public void setGridHeight(int height) {
        this.gridHeight = height;
    }

    public void setGridWidth(int width) {
        this.gridWidth = width;
    }

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public void nextStop(Product location) {
        currentPosition = new Product(location);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int squareWidth = squareSize / gridWidth;
        int squareHeight = squareSize / gridHeight;

        // converteer de graphics naar een Graphics2D zodat we de lijn dikte kunnen aanpassen
        Graphics2D g2 = (Graphics2D) g;

        // start x en y zijn de offset waardes om vandaan te rekenen
        int startX = 20;
        int startY = 30;

        g2.setStroke(new BasicStroke(1));
        // maak de vertikale lijnen
        for (int i = 0; i < gridWidth + 1; i++) {
            g2.setColor(Color.black);
            g2.drawLine(startX+ squareWidth *i, startY, startX+ squareWidth *i, startY + squareHeight * gridHeight);
        }
        // maak de horizontale lijnen
        for (int i = 0; i < gridHeight + 1; i++) {
            g2.setColor(Color.black);
            g2.drawLine(startX, startY+ squareHeight *i, startX + squareWidth * gridWidth, startY+ squareHeight *i);
        }
        if (this.producten != null) {
            // als er producten aanwezig zijn vul de grid met de producten
            for (int i = 0; i < this.producten.size(); i++) {
                // teken een achtergrondje voor de producten zodat je makkelijker kan zien waar een product zit
                int x = this.producten.get(i).getX() * squareWidth + startX;
                int y = (startY + gridHeight * squareHeight) - (squareHeight * this.producten.get(i).getY());

                g2.setColor(Color.black);
                g2.fillRect(x + 3, y - 3 - squareHeight + 6, squareWidth - 5,  squareHeight - 5);
            }
            Color kleur = Color.green;
            for (int i = 0; i < this.producten.size() - 1; i++) {
                // teken een lijn in 3 kleuren tussen de producten zodat je makkelijk kan zien dat een rechte lijn onderbroken is
                // bereken de positie van punt 1 en punt 2 (het midden van van het vakje van de bijbehorende product)
                int x1 = this.producten.get(i).getX() * squareWidth + startX + squareWidth /2;
                int y1 = (startY + gridHeight * squareHeight) - ((squareHeight / 2) + squareHeight * this.producten.get(i).getY());
                int x2 = this.producten.get(i + 1).getX() * squareWidth + startX + squareWidth /2;
                int y2 = (startY + gridHeight * squareHeight) - ((squareHeight / 2) + squareHeight * this.producten.get(i + 1).getY());

                // kies de kleur aan de hand van modulo
                if (!isLive) {
                    if (i % 3 == 0) {
                        g2.setColor(Color.red);
                    } else if (i % 3 == 1) {
                        g2.setColor(Color.green);
                    } else {
                        g2.setColor(Color.yellow);
                    }
                } else {
                    if (this.producten.get(i).compareXY(currentPosition)) {
                        kleur = Color.red;
                    }
                    g2.setColor(kleur);
                }

                g2.setStroke(new BasicStroke(3));
                g2.draw(new Line2D.Float(x1, y1, x2, y2));
            }
        }
    }
}
