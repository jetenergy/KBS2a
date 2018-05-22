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

    public CPositionPanel() {

        setBorder(border);
        JLabel title = new JLabel("Beste oplossing");
        add(title);
    }

    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int height = 5;
        int width = 5;
        int startX = 20;
        int startY = 30;
        int squaresize = 50;

        for (int i = 0; i < width + 1; i++) {
            g.setColor(Color.black);
            g.drawRect(startX+ squaresize*i, startY, 1, squaresize*height +1);
        }
        for (int i = 0; i < height + 1; i++) {
            g.setColor(Color.black);
            g.drawRect(startX, startY+ squaresize*i, squaresize*width +1, 1);
        }
        if (this.producten != null) {
            for (int i = 1; i < this.producten.size(); i++) {
                int x1 = this.producten.get(i - 1).getX() * squaresize + startX + squaresize/2;
                int y1 = (startY + height * squaresize) - ((squaresize / 2) + squaresize * this.producten.get(i - 1).getY());
                int x2 = this.producten.get(i).getX() * squaresize + startX + squaresize/2;
                int y2 = (startY + height * squaresize) - ((squaresize / 2) + squaresize * this.producten.get(i).getY());

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
