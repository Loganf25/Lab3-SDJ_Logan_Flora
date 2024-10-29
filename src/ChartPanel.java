package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.*;
import org.jfree.chart.*;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;

public class ChartPanel extends JPanel {

    private final HashMap<String, ImageIcon> teamLogos = loadTeamLogos();
    private HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData;

    public ChartPanel() {
        setPreferredSize(new Dimension(700, 550));
        Color PearlRubyRed = new Color(114, 20, 34);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(PearlRubyRed);

        NFLData = new HashMap<>();
    }

    public void setMap(HashMap<String, ArrayList<NFLTeamStatsByYear>> data) {
        NFLData = data;
    }

    //Using Team Logos as Key for Chart, this gets them
    private HashMap<String, ImageIcon> loadTeamLogos() {
        //Using Hashmap for each call by team name
        HashMap<String, ImageIcon> teamLogos = new HashMap<>();
        try {
            File logoFolder = new File("resources/logos");
            for (File logo : Objects.requireNonNull(logoFolder.listFiles())) {
                String teamName = logo.getName().replace(".png", "");
                teamLogos.put(teamName, new ImageIcon(logo.getPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teamLogos;
    }

    //Main Function that Populates the Charts, based on filters/chartType
    public void updateChart(String selectedChart, boolean oneYearFlag) {
        removeAll();
        JFreeChart chart = null;

        //Decide Which Graph is to be display
        if(oneYearFlag && NFLData.size() == 1)
            chart = displayPieGraph(selectedChart);
        else if (oneYearFlag && NFLData.size() > 1)
            chart = displayBarGraph(selectedChart);
        else
            chart = displayLineGraph(selectedChart);

        if (chart != null) {
            JPanel chartHolder = new JPanel (new BorderLayout());
            chartHolder.setPreferredSize(new Dimension(700, 525));
            org.jfree.chart.ChartPanel actChart = new org.jfree.chart.ChartPanel(chart);
            chartHolder.add(actChart, BorderLayout.CENTER);
            add(Box.createVerticalGlue());
            add(chartHolder);
            add(Box.createVerticalGlue());
        }
        else{
            JLabel noChart = new JLabel("No Stats to Compare for " + NFLData.entrySet().iterator().next().getValue().getFirst().getTeamName() + " during this Season");
            add(noChart, BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    private JFreeChart displayPieGraph(String chartType){
        //Displays a Pie chart if One Team and One Year
        JFreeChart chart = null;
        //Holds Data for Graph, specific dataType of JFreeChart
        DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();
        //Makes calls into NFLData easier
        Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team = NFLData.entrySet().iterator().next();
        NFLTeamStatsByYear teamStats = team.getValue().getFirst();

        //Gets the Selected Chart (Wins/Yards/Touchdowns)
        switch (chartType) {
            //Compares Teams Seasons Wins to Losses
            case "Wins":
                dataset.setValue("Wins", teamStats.getWins());
                dataset.setValue("Losses", teamStats.getLosses());
                chart = ChartFactory.createPieChart("Wins/Losses for " + teamStats.getTeamName() + " in " + teamStats.getYear(), dataset, true, true, false);
                break;
            case "Yards":
                dataset.setValue("Passing Yards", teamStats.getPassing());
                dataset.setValue("Rushing Yards", teamStats.getRushing());
                chart = ChartFactory.createPieChart("Passing/Rushing Yards for " + teamStats.getTeamName() + " in " + teamStats.getYear(), dataset, true, true, false);
            case "Touchdowns":
                dataset.setValue("Passing Touchdowns", teamStats.getPassTds());
                dataset.setValue("Rushing Touchdowns", teamStats.getRushingTds());
                chart = ChartFactory.createPieChart("Passing/Rushing TDs for " + teamStats.getTeamName() + " in " + teamStats.getYear(), dataset, true, true, false);
                break;
            case null:
                break;
            default:
                break;
        }
        return chart;
    }

    private JFreeChart displayBarGraph(String chartType){
        //Displays a Bar Graph if All teams and One Year
        JFreeChart chart = null;
        //Holds data for Chart
        XYSeriesCollection dataset = new XYSeriesCollection();

        //Iterates through each team, only 1 year for each so no ArrayList iteration
        int xValue = 1;
        for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : NFLData.entrySet()) {
            //Will hold the value being displayed (Wins, Yards, or Touchdowns)
            int valueQ = 0;
            XYSeries series = new XYSeries(team.getKey());
            //Decide which value is being displayed
            if(!team.getValue().isEmpty()) {
                NFLTeamStatsByYear teamStats = team.getValue().getFirst();
                assert chartType != null;
                valueQ = switch (chartType) {
                    case "Wins" -> teamStats.getWins();
                    case "Yards" -> teamStats.getTotalYards();
                    case "Touchdowns" -> teamStats.getPassTds() + teamStats.getRushingTds();
                    default -> valueQ;
                };
                //Place into dataset for graph
                series.add(xValue++, valueQ);
            }
            dataset.addSeries(series);
        }
        chart = ChartFactory.createXYBarChart(chartType + " for all Teams in " + NFLData.values().iterator().next().getFirst().getYear(),
                "Team", false, chartType, dataset, PlotOrientation.VERTICAL, true, true, false);
        //Gets Icon and puts it in its spot
        XYPlot plot = (XYPlot) chart.getPlot();
        return chart;
    }


    private JFreeChart displayLineGraph(String chartType){
        //Displays a Line Graph if All Teams/One Team and All Years
        //Since this display a line, it needs an XY dataset
        JFreeChart chart = null;
        XYSeriesCollection dataset = new XYSeriesCollection();

        //Iterate Through Teams
        for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : NFLData.entrySet()) {
            //Actual XY series for each years point on graph
            XYSeries series = new XYSeries(team.getKey());
            //Iterate Through NFL Stats
            for (NFLTeamStatsByYear teamStats : team.getValue()) {
                int valueQ = 0;
                assert chartType != null;
                switch (chartType) {
                    case "Wins" -> valueQ = teamStats.getWins();
                    case "Yards" -> valueQ = teamStats.getTotalYards();
                    case "Touchdowns" -> valueQ = teamStats.getPassTds() + teamStats.getRushingTds();
                }
                series.add((double) teamStats.getYear(), valueQ);
            }
            dataset.addSeries(series);
            //Create Chart
            chart = ChartFactory.createXYLineChart(chartType + " Over Seasons", "Year", chartType, dataset, PlotOrientation.VERTICAL, true, true, false);
            XYPlot plot = (XYPlot) chart.getPlot();
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

            plot.setRenderer(renderer);
        }
        return chart;
    }

}

