package src;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel{

    public DetailsPanel(){
        setPreferredSize(new Dimension(500,500));
        Color CobaltBlue = new Color(37, 41, 74);
        setBackground(CobaltBlue);
    }

    public void updateDetails(NFLTeamStatsByYear stats){
        this.removeAll();
        //Set up in a series of layouts and panels for a neat visual
        //Layout for Details Panel is North/South
        setLayout(new BorderLayout());

        //Contains Opener Panel (Major Stats) and Bulk Panel (Rest of Stats)
        //Layout for Opener Panel is North/South
        JPanel opener = new JPanel();
        opener.setLayout(new BorderLayout());
        //Contains Title (Team n Year) and Big Stat (Wins/Losses)
        //Title Panel
        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());
        //Team Name Label
        JLabel teamName = new JLabel("Stats for " + stats.getTeamName());
        teamName.setHorizontalAlignment(JLabel.CENTER);
        title.add(teamName, BorderLayout.NORTH);
        //Year Label
        JLabel year = new JLabel("for the year " + stats.getYear());
        year.setHorizontalAlignment(JLabel.CENTER);
        title.add(year, BorderLayout.SOUTH);
        //Big Stats (Wins/Losses)
        JPanel bigStats = new JPanel();
        bigStats.setLayout(new BoxLayout(bigStats, BoxLayout.X_AXIS));
        //Wins
        JLabel wins = new JLabel("Wins: " + stats.getWins());
        bigStats.add(Box.createHorizontalGlue());
        bigStats.add(wins);
        //Losses
        JLabel losses = new JLabel("Losses: " + stats.getLosses());
        bigStats.add(Box.createHorizontalGlue());
        bigStats.add(losses);
        //Final glue is to get equal distance between sides and each other
        bigStats.add(Box.createHorizontalGlue());
        //Opener Panel Finishing Logic
        opener.add(title, BorderLayout.NORTH);
        opener.add(bigStats, BorderLayout.CENTER);

        //Layout for Bulk Panel is North/South
        JPanel bulk = new JPanel();
        bulk.setLayout(new BoxLayout(bulk, BoxLayout.Y_AXIS));
        bulk.add(Box.createVerticalGlue());
        //Contains 2 Panels bulkStats and badStats
        //Bulk Stats
        JPanel bulkStats = new JPanel();
        bulkStats.setLayout(new BorderLayout());
        //Contains 2 more Panels leftS and rightS
        //Left Stats Panel
        JPanel leftS = new JPanel();
        leftS.setLayout(new BoxLayout(leftS, BoxLayout.Y_AXIS));
        //Contains yards and points Stats
        JLabel totalYards = new JLabel("• Total Yards: " + stats.getTotalYards());
        JLabel rushingYards = new JLabel("  - Rushing Yards: " + stats.getRushing());
        JLabel passingYards = new JLabel("  - Passing Yards: " + stats.getPassing());
        JLabel pointsScored = new JLabel("• Points Scored: " + stats.getPointsScored());
        JLabel pointsAllowed = new JLabel("• Points Allowed: " + stats.getPointsAllowed());
        //Equal Spacing to fill panel
        leftS.add(Box.createVerticalGlue());
        leftS.add(totalYards);
        //leftS.add(Box.createVerticalGlue());
        leftS.add(rushingYards);
        //leftS.add(Box.createVerticalGlue());
        leftS.add(passingYards);
        leftS.add(Box.createVerticalGlue());
        leftS.add(pointsScored);
        //leftS.add(Box.createVerticalGlue());
        leftS.add(pointsAllowed);
        leftS.add(Box.createVerticalGlue());
        bulkStats.add(leftS, BorderLayout.WEST);
        //Right Stats Panel
        JPanel rightS = new JPanel();
        rightS.setLayout(new BoxLayout(rightS, BoxLayout.Y_AXIS));
        //Contains rest of passing Stats and td Stats
        JLabel passAmt = new JLabel("• Passing Attempts: " + stats.getPassAtt());
        JLabel passComp = new JLabel("• Passing Completions: " + stats.getPassCmp());
        JLabel passingTd = new JLabel("• Passing TDs: " + stats.getPassTds());
        JLabel rushingTd = new JLabel("• Rushing TDs: " + stats.getRushingTds());
        rightS.add(Box.createVerticalGlue());
        rightS.add(passAmt);
        //rightS.add(Box.createVerticalGlue());
        rightS.add(passComp);
        rightS.add(Box.createVerticalGlue());
        rightS.add(passingTd);
        //rightS.add(Box.createVerticalGlue());
        rightS.add(rushingTd);
        rightS.add(Box.createVerticalGlue());
        bulkStats.add(rightS, BorderLayout.EAST);

        //Doesn't make is scrollable, but makes it look better
        //And Bad Stats are visible now
        JScrollPane scrollPane = new JScrollPane(bulkStats);
        bulk.add(scrollPane);
        bulk.add(Box.createVerticalGlue());
        //Bad Stats Panel
        JPanel badStats = new JPanel();
        badStats.setMinimumSize(new Dimension(0, 100)); // Example minimum height of 40 pixels
        badStats.setLayout(new BoxLayout(badStats, BoxLayout.X_AXIS));
        //Turnovers
        JLabel turnovers = new JLabel("Turnovers: " + stats.getTurnOvers());
        badStats.add(Box.createHorizontalGlue());
        badStats.add(turnovers);
        //Penalties
        JLabel penalties = new JLabel("Penalties: " + stats.getPenalties());
        badStats.add(Box.createHorizontalGlue());
        badStats.add(penalties);
        //Final glue is to get equal distance between sides and each other
        badStats.add(Box.createHorizontalGlue());

        bulk.add(badStats);
        bulk.add(Box.createVerticalGlue());
        add(opener, BorderLayout.NORTH);
        add(bulk, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}

