package com.m2e4.gui.bpp;

import com.m2e4.DataBase.Product;
import com.m2e4.algorithm.Box;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BoxDrawPanel extends JPanel {
    private ArrayList<Box> boxes = new ArrayList<>();
    private ArrayList<String> itemsFilled = new ArrayList<>();
    private int sizeFactor = 25;

    public void setBoxes(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    public void setItemFilled(String item) {
        itemsFilled.add(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        int xBoxOffset = 20;
        int yBoxOffset = 30;
        int xItemOffset = 3;
        int yItemOffset = 3;

        for (Box b : boxes) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);
            g2.drawRect(xBoxOffset, yBoxOffset, (int)(b.getHeight() * sizeFactor + (xItemOffset * 2)), 49 + (yItemOffset * 2));

            int pIndex = 0;
            double xOffset = xItemOffset;
            for (Product p : b.getItems()) {
                if (itemsFilled.contains(p.getNaam())) {
                    g2.setColor(Color.GREEN);
                } else {
                    g2.setColor((pIndex % 2) == 1 ? Color.ORANGE : Color.YELLOW);
                }
                g2.fillRect((int)Math.round(xBoxOffset + xOffset), yBoxOffset + yItemOffset, (int)(p.getHoogte() * (double)sizeFactor), 50);
                g2.setColor(Color.BLACK);
                g2.drawString(new DecimalFormat("#.##").format(p.getHoogte()), (int)Math.round(xBoxOffset + xOffset) + 5, yBoxOffset + yItemOffset + 15);
                g2.drawString(p.getNaam(), (int)Math.round(xBoxOffset + xOffset) + 5, yBoxOffset + yItemOffset + 30);
                xOffset += Math.floor(p.getHoogte() * (double)sizeFactor);
                ++pIndex;
            }

            yBoxOffset += 60;
        }
    }
}
