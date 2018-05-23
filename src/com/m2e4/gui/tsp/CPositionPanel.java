package com.m2e4.gui.tsp;

import com.m2e4.DataBase.Product;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class CPositionPanel extends JPanel{

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private ArrayList<Product> producten;
    private int gridHeight = 5;
    private int gridWidth = 5;
    private int squareSize = 50;

    public CPositionPanel(String label) {

        setBorder(border);
        JLabel title = new JLabel(label);
        add(title);
    }

    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int squareWidth = squareSize * 5 / gridWidth;
        int squareHeight = squareSize * 5 / gridHeight;

        Graphics2D g2 = (Graphics2D) g;

        int startX = 20;
        int startY = 30;
        g2.setStroke(new BasicStroke(1));
        for (int i = 0; i < gridWidth + 1; i++) {
            g2.setColor(Color.black);
            g2.drawRect(startX+ squareWidth *i, startY, 1, squareHeight * gridHeight +1);
        }
        for (int i = 0; i < gridHeight + 1; i++) {
            g2.setColor(Color.black);
            g2.drawRect(startX, startY+ squareHeight *i, squareWidth * gridWidth +1, 1);
        }
        if (this.producten != null) {
            for (int i = 1; i < this.producten.size(); i++) {
                int x1 = this.producten.get(i - 1).getX() * squareWidth + startX + squareWidth /2;
                int y1 = (startY + gridHeight * squareHeight) - ((squareHeight / 2) + squareHeight * this.producten.get(i - 1).getY());
                int x2 = this.producten.get(i).getX() * squareWidth + startX + squareWidth /2;
                int y2 = (startY + gridHeight * squareHeight) - ((squareHeight / 2) + squareHeight * this.producten.get(i).getY());

                if (i % 3 == 0) {
                    g2.setColor(Color.red);
                } else if (i % 3 == 1) {
                    g2.setColor(Color.green);
                } else {
                    g2.setColor(Color.yellow);
                }

                g2.setStroke(new BasicStroke(3));
                g2.draw(new Line2D.Float(x1, y1, x2, y2));
            }
        }
    }
}
