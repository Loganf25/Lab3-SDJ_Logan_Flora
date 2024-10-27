package src;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel{
    private final Color CobaltBlue = new Color(37, 41, 74);


    public DetailsPanel(){
        setPreferredSize(new Dimension(500,500));
        setBackground(CobaltBlue);
        setLayout(new BorderLayout());

        //Creates a Label for When Empty
        JLabel emptyMesg = new JLabel("Click \"Details\" for Team's Season Stats");
        emptyMesg.setForeground(Color.LIGHT_GRAY);
        emptyMesg.setFont(new Font("Time New Roman", Font.PLAIN, 15));
        emptyMesg.setHorizontalAlignment(JLabel.CENTER);
        add(emptyMesg, BorderLayout.CENTER);
    }

    public void updateDetails(NFLTeamStatsByYear stats){
        this.removeAll();
        //Set up in a series of layouts and panels for a neat visual
        //Layout for Details Panel is North/South

        //Contains Opener Panel (Major Stats) and Bulk Panel (Rest of Stats)
        //Layout for Opener Panel is North/South
        JPanel opener = new JPanel();
        opener.setLayout(new BorderLayout());

        //Contains Title (Team n Year)
        // and Big Stat (Wins/Losses)
        //Title Panel
        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());
        title.setBackground(CobaltBlue);

        //Team Name Label
        JLabel teamName = new JLabel("Stats for " + stats.getTeamName());
        teamName.setHorizontalAlignment(JLabel.CENTER);
        teamName.setForeground(Color.WHITE);
        title.add(teamName, BorderLayout.NORTH);

        //Year Label
        JLabel year = new JLabel("for the year " + stats.getYear());
        year.setHorizontalAlignment(JLabel.CENTER);
        year.setForeground(Color.WHITE);
        title.add(year, BorderLayout.SOUTH);

        //Big Stats (Wins/Losses) Panel
        JPanel bigStats = new JPanel();
        bigStats.setLayout(new BoxLayout(bigStats, BoxLayout.X_AXIS));
        bigStats.setBackground(new Color(50, 55, 90));

        //Wins Label
        JLabel wins = new JLabel("Wins: " + stats.getWins());
        wins.setForeground(Color.WHITE);
        bigStats.add(Box.createHorizontalGlue());
        bigStats.add(wins);

        //Losses Label
        JLabel losses = new JLabel("Losses: " + stats.getLosses());
        losses.setForeground(Color.WHITE);
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
        bulk.setBackground(new Color(60, 65, 105));
        bulk.add(Box.createVerticalGlue());

        //Contains 2 Panels bulkStats and badStats
        //Bulk Stats Panel
        JPanel bulkStats = new JPanel();
        bulkStats.setLayout(new BorderLayout());
        bulkStats.setBackground(new Color(60, 65, 105));

        //Contains 2 more Panels leftS and rightS
        //Left Stats Panel
        JPanel leftS = new JPanel();
        leftS.setLayout(new BoxLayout(leftS, BoxLayout.Y_AXIS));
        leftS.setBackground(new Color(60, 65, 105));

        //Contains yards and points Stats (Labels)
        JLabel totalYards = new JLabel("• Total Yards: " + stats.getTotalYards());
        totalYards.setForeground(Color.WHITE);
        JLabel rushingYards = new JLabel("  - Rushing Yards: " + stats.getRushing());
        rushingYards.setForeground(Color.WHITE);
        JLabel passingYards = new JLabel("  - Passing Yards: " + stats.getPassing());
        passingYards.setForeground(Color.WHITE);
        JLabel pointsScored = new JLabel("• Points Scored: " + stats.getPointsScored());
        pointsScored.setForeground(Color.WHITE);
        JLabel pointsAllowed = new JLabel("• Points Allowed: " + stats.getPointsAllowed());
        pointsAllowed.setForeground(Color.WHITE);
        //Equal Spacing to fill panel
        leftS.add(Box.createVerticalGlue());
        leftS.add(totalYards);
        leftS.add(rushingYards);
        leftS.add(passingYards);
        leftS.add(Box.createVerticalGlue());
        leftS.add(pointsScored);
        leftS.add(pointsAllowed);
        leftS.add(Box.createVerticalGlue());
        bulkStats.add(leftS, BorderLayout.WEST);

        //Right Stats Panel
        JPanel rightS = new JPanel();
        rightS.setLayout(new BoxLayout(rightS, BoxLayout.Y_AXIS));
        rightS.setBackground(new Color(60, 65, 105));

        //Contains rest of passing Stats and td Stats (Labels)
        JLabel passAmt = new JLabel("• Passing Attempts: " + stats.getPassAtt());
        passAmt.setForeground(Color.WHITE);
        JLabel passComp = new JLabel("• Passing Completions: " + stats.getPassCmp());
        passComp.setForeground(Color.WHITE);
        JLabel passingTd = new JLabel("• Passing TDs: " + stats.getPassTds());
        passingTd.setForeground(Color.WHITE);
        JLabel rushingTd = new JLabel("• Rushing TDs: " + stats.getRushingTds());
        rushingTd.setForeground(Color.WHITE);
        rightS.add(Box.createVerticalGlue());
        rightS.add(passAmt);
        rightS.add(passComp);
        rightS.add(Box.createVerticalGlue());
        rightS.add(passingTd);
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
        badStats.setBackground(CobaltBlue);

        //Turnovers Label
        JLabel turnovers = new JLabel("Turnovers: " + stats.getTurnOvers());
        turnovers.setForeground(Color.WHITE);
        badStats.add(Box.createHorizontalGlue());
        badStats.add(turnovers);

        //Penalties Label
        JLabel penalties = new JLabel("Penalties: " + stats.getPenalties());
        penalties.setForeground(Color.WHITE);
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

