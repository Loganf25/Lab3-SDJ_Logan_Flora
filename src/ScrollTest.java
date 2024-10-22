package src;

import javax.swing.*;
import java.awt.*;

public class ScrollTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Scroll Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        // Add a bunch of labels to the panel
        for (int i = 0; i < 50; i++) {
            panel.add(new JLabel("Label " + i));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.revalidate();
        frame.repaint();


        frame.setVisible(true);
    }
}