package com.m2e4.gui.tsp;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CItemPanel extends JPanel{

    private Object[][] itemData;
    private Object[] columnNames = new Object[]{"Item", "X" , "Y"};

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private JTable itemTable = new JTable((itemData != null ? itemData : new Object[][]{}), columnNames);

    public CItemPanel() {
        setLayout(new FlowLayout());
        setBorder(border);
        {
            JPanel container = new JPanel();
//            container.setLayout(new GridLayout(2, 1));
            container.setLayout(new BorderLayout());
            container.add(itemTable.getTableHeader(), BorderLayout.NORTH);
            container.add(itemTable);

            JScrollPane scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(250, 300));
            add(scrollPane);
        }
    }
}
