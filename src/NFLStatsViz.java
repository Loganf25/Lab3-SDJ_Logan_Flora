package src;

import javax.swing.*;
import java.awt.*;

public class NFLStatsViz {
    //Main Window Program to hold other 4 Panels
    public static void main(String[] args){
        JFrame frame = new JFrame("NFL Stats Visualized (2003-)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setBackground(Color.MAGENTA);

        //Initialize all Panels before adding to this Panel
        //As the TablePanel affect the other 3, therefore needs access to em
        DetailsPanel detailsPanel = new DetailsPanel();
        StatsPanel statsPanel = new StatsPanel();
        ChartPanel chartPanel = new ChartPanel();
        TablePanel tablePanel = new TablePanel(detailsPanel, statsPanel, chartPanel);

        //Created a Left and Right Panel (I wanted different vert. lengths)
        //Left Holds Table and Stats Panels
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(500, 1000));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createVerticalGlue());
        tablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(tablePanel);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(statsPanel);

        //Right Holds Chart and Details Panels
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(500, 1000));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        chartPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        detailsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(chartPanel);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(detailsPanel);

        //Add Left and Right Panel
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);

        //Logic Pt.2
        frame.pack();
        frame.setVisible(true);
    }

}
