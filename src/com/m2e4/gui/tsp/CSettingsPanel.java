package com.m2e4.gui.tsp;

import com.m2e4.DataBase.OrderFactory;
import com.m2e4.DataBase.Product;
import com.m2e4.gui.TspCFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class CSettingsPanel extends JPanel{

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JButton startControl = new JButton("Start");
    private JButton stopControl = new JButton("Stop");
    private JButton addOrderControl = new JButton("Kies bestelling");

    private TspCFrame parent;

    public CSettingsPanel(TspCFrame parent) {
        setLayout(new FlowLayout());
        setBorder(border);
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(4);

        // deze jpanel word gebruikt om de knoppen onder elkaar te plaatssen
        JPanel buttons = new JPanel();
        buttons.setLayout(layout);
        {
            // parent is het "parent" element en arduinoHere() vraagd aan het tsp paneel of hij een arduino heeft
            startControl.addActionListener(e -> {
                if (parent.arduinoHere()) startResume();
            });
            stopControl.setEnabled(false);
            stopControl.addActionListener(e -> stop());
            addOrderControl.addActionListener(e -> addOrder());

            buttons.add(startControl);
            buttons.add(stopControl);
            buttons.add(addOrderControl);
        }
        add(buttons);

        this.parent = parent;
    }

    private void startResume() {
        startControl.setEnabled(false);
        stopControl.setEnabled(true);
        // roept startAlgo() aan op het tspcontrol frame
        parent.startAlgo();
    }

    private void stop() {
        startControl.setEnabled(true);
        stopControl.setEnabled(false);
    }

    private void addOrder() {
        // hiermee kunnen we het json bestand kiezen
        JFileChooser fileChooser = new JFileChooser();
        int rval = fileChooser.showOpenDialog(parent);

        if (rval != JFileChooser.APPROVE_OPTION) return;
        File file = fileChooser.getSelectedFile();

        ArrayList<Product> products;
        try {
            products = OrderFactory.processJsonOrder(new FileReader(file));
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        // zorgt ervoor dat je niet een lege bestelling kan kiezen
        if (products.size() != 0) parent.setItems(products);
    }
}
