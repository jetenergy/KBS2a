package com.m2e4.gui.tsp;

import com.m2e4.DataBase.Product;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class CPositionPanel extends JPanel{

    private JPanel solutionPanel;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private ArrayList<Product> producten;

    public CPositionPanel() {

        setBorder(border);
        JLabel title = new JLabel("Beste oplossing");
        add(title);

        solutionPanel = new JPanel();
        add(solutionPanel);
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
            System.out.println(this.producten);
            this.producten.size();
            for (int i = 1; i < this.producten.size(); i++) {
                int x1 = this.producten.get(i - 1).getX() * squaresize + startX + squaresize/2;
                int y1 = this.producten.get(i - 1).getY() * squaresize + startY + squaresize/2;
                int x2 = this.producten.get(i).getX() * squaresize + startX + squaresize/2;
                int y2 = this.producten.get(i).getY() * squaresize + startY + squaresize/2;

                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(3));
                g2.draw(new Line2D.Float(x1, y1, x2, y2));
                System.out.println("x1," + this.producten.get(i - 1).getX() + ";  y1," + this.producten.get(i - 1).getY() +
                        ";  x2," + this.producten.get(i).getX() + ";  y2," + this.producten.get(i).getY());
            }
        }
    }
}
