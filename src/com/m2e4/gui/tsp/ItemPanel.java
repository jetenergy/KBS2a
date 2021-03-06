package com.m2e4.gui.tsp;

import com.m2e4.DataBase.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ItemPanel extends JPanel{

    private Object[][] itemData;
    private String[] columnNames = new String[]{"Item", "X" , "Y"};

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private JTable itemTable = new JTable((itemData != null ? itemData : new Object[][]{}), columnNames);

    public ItemPanel() {
        setLayout(new FlowLayout());
        setBorder(border);
        {
            JPanel container = new JPanel();
            container.setLayout(new BorderLayout());
            container.add(itemTable.getTableHeader(), BorderLayout.NORTH);
            container.add(itemTable);

            JScrollPane scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(250, 300));
            add(scrollPane);
        }
    }

    public void setTable(ArrayList<Product> producten) {
        // verander de arraylist naar de object array die de table nodig heeft
        itemData = new Object[producten.size()][];
        for (int i = 0; i < producten.size(); ++i) {
            itemData[i] = new Object[] { producten.get(i).getNaam(), producten.get(i).getX() , producten.get(i).getY()};
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(itemData, columnNames);
        itemTable.setModel(model);
        itemTable.setDefaultEditor(Object.class, null);
    }
}
