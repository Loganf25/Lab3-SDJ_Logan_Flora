package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static src.vizDataBack.*;


public class StatsPanel extends JPanel implements DataObserver{

    public StatsPanel(){
        setPreferredSize(new Dimension(500,350));
        Color blackGrn = new Color(52, 62,64);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(blackGrn);
    }

    @Override
    public void update(HashMap<String, ArrayList<NFLTeamStatsByYear>> data, String noWorry, NFLTeamStatsByYear noWorry2) {
        populate(data);
    }

    public void populate(HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        removeAll();
        //Title (Details where Average comes from)
        String title;
        //One Team, One Year
        if(NFLData.size() == 1 && NFLData.values().iterator().next().size() == 1){
            HashMap.Entry<String, ArrayList<NFLTeamStatsByYear>> start = NFLData.entrySet().iterator().next();
            title = "Averages of " + start.getKey() + " in " + start.getValue().getFirst().getYear();
        }
        //If Only One Team, All Seasons
        else if (NFLData.size() == 1)
            title = "Averages of " + NFLData.keySet().iterator().next() + "'s Seasons";
        //All teams, One Year
        else if (NFLData.size() > 1 && NFLData.values().iterator().next().size()== 1)
            title = "Averages of All Teams in " + NFLData.values().iterator().next().getFirst().getYear();
        //All Teams, All Years
        else
            title = "Averages of All Team's Seasons";
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);
        add(Box.createVerticalGlue());

        //Average Points Stat
        double avgPoints = averagePointsScored(NFLData);
        JLabel avgPointsLabel = new JLabel("Avg Points: " + String.format("%.2f", avgPoints));
        avgPointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        avgPointsLabel.setForeground(Color.WHITE);
        add(Box.createHorizontalGlue());
        add(avgPointsLabel);
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());


        //Average Total Yards Stat
        double averageTotalYards = averageTotalYards(NFLData);
        JLabel averageTotalYardsLabel = new JLabel("Average Total Yards: " + String.format("%.2f", averageTotalYards));
        averageTotalYardsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageTotalYardsLabel.setForeground(Color.WHITE);
        add(Box.createHorizontalGlue());
        add(averageTotalYardsLabel);
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());

        //Average Turnovers Stat
        double averageTurnovers = averageTurnovers(NFLData);
        JLabel averageTurnoversLabel = new JLabel("Average Turnovers: " + String.format("%.2f", averageTurnovers));
        averageTurnoversLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageTurnoversLabel.setForeground(Color.WHITE);
        add(Box.createHorizontalGlue());
        add(averageTurnoversLabel);
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());


        revalidate();
        repaint();
    }
}

