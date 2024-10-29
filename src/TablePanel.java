package src;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;

import static src.vizDataBack.*;

public class TablePanel extends JPanel{
    //Data taken from csv file
    private final HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData;
   //Panels to be affected
    private final DetailsPanel detailsPanel;
    private final StatsPanel statsPanel;
    private final ChartPanel chartPanel;
    private final JPanel displayPanel;
    //Components Used
    private final JComboBox<String> sortDropDown;
    private final String[] SORT_OPTIONS = {"Team Name (A-Z)", "Team Name (Z-A)", "Year (2003-2023)", "Year (2023-2003)"};
    private final JComboBox<String> teamFilter;
    private final JComboBox<Integer> yearFilter;
    private final JTextField winsFilter;
    private final JTextField lossesFilter;
    private final String[] CHART_OPTIONS = {"Wins", "Yards", "Touchdowns"};
    private final JComboBox<String> chartType;

    //Constructor
    public TablePanel(DetailsPanel detailsPanel, StatsPanel statsPanel, ChartPanel chartPanel){
        //Panel Setters as they will be affected
        this.detailsPanel = detailsPanel;
        this.statsPanel = statsPanel;
        this.chartPanel = chartPanel;
        NFLData = getTeamsFromCSV("resources/NFLteam_stats_2023.csv");
        //This is needed to reset the data when reverting back from a filter
        //Too deep ;(
        setBackground(Color.BLACK);

        //Control Panel to hold filters and sorts
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(500, 60));
        Color brillBlue = new Color(62, 95, 138);
        controlPanel.setBackground(brillBlue);

        //Inside Control Panel
        //Sort Drop Down Menu
        sortDropDown = new JComboBox<>(SORT_OPTIONS);
        sortDropDown.insertItemAt(null, 0);
        sortDropDown.setSelectedIndex(0);
        sortDropDown.addActionListener(e -> updateDisplay());
        controlPanel.add(sortDropDown);

        //Drop Down for picking a Team
        String[] teamNames = NFLData.keySet().toArray(new String[0]);
        teamFilter = new JComboBox<>(teamNames);
        teamFilter.insertItemAt("All Teams", 0);
        teamFilter.setSelectedIndex(0);
        teamFilter.addActionListener(e -> updateDisplay());
        controlPanel.add(teamFilter);

        //Drop Down for picking a Year
        Integer[] yearOfStats = IntStream.rangeClosed(2003, 2023).boxed().toArray(Integer[]::new);
        yearFilter = new JComboBox<>(yearOfStats);
        yearFilter.insertItemAt(null, 0);
        yearFilter.setSelectedIndex(0);
        yearFilter.addActionListener(e -> updateDisplay());
        controlPanel.add(yearFilter);

        //TextField for Wins Filter
        winsFilter = new JTextField(5);
        winsFilter.addActionListener(e -> updateDisplay());
        controlPanel.add(new JLabel("Minimum Wins: "));
        controlPanel.add(winsFilter);

        //TextField for Losses Filter
        lossesFilter = new JTextField(5);
        lossesFilter.addActionListener(e -> updateDisplay());
        controlPanel.add(new JLabel("Minimum Losses: "));
        controlPanel.add(lossesFilter);

        //JComboBox for Chart Stat
        chartType = new JComboBox<>(CHART_OPTIONS);
        chartType.addActionListener(e -> updateDisplay());
        controlPanel.add(chartType);

        //Display Panel (Actual List)
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setBackground(Color.CYAN);
        //Populate the displayPanel upon create so as not to be empty
        updateDisplay();

        //Turns displayPanel into a scrollable panel, lots of data
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 600));
        //Add to table Panel
        add(Box.createVerticalGlue());
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    private HashMap<String, ArrayList<NFLTeamStatsByYear>> filter(HashMap<String, ArrayList<NFLTeamStatsByYear>> toFilterData){
        String selectedTeam = (String) teamFilter.getSelectedItem();
        Integer selectedYear = (Integer) yearFilter.getSelectedItem();
        int minWins = 0;
        int minLosses = 0;
        //Gets minWins and minLosses
        try {
            minWins = Integer.parseInt(winsFilter.getText());
        } catch (NumberFormatException ignored) {}
        try {
            minLosses = Integer.parseInt(lossesFilter.getText());
        } catch (NumberFormatException ignored) {}

        //New Map Only containing filtered content
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = new HashMap<>();
        //Iterates Through Key (Teams)
        for(HashMap.Entry<String, ArrayList<NFLTeamStatsByYear>> team : toFilterData.entrySet()){
            //Stores Filtered Stats in Value (ArrayList) (Stats)
            ArrayList<NFLTeamStatsByYear> filteredStats = new ArrayList<>();
            //Iterates through all seasons Stats Object
            for(NFLTeamStatsByYear teamStats : team.getValue()){
                //Needs to pass all filters in order to get placed into new array
                if (filterByYear(selectedYear).test(teamStats)) {
                    if(filterByWins(minWins).test(teamStats)) {
                        if(filterByLosses(minLosses).test(teamStats)) {
                            filteredStats.add(teamStats);
                        }
                    }
                }
            }
            //If that team is alo filtered in, that team, and the new Array list
            //Are added to new Hashmap
            if (filterByTeam(selectedTeam).test(team))
                filteredData.put(team.getKey(), filteredStats);
        }
        return filteredData;
    }

    private void updateDisplay() {
        //Reset entire Panel
        displayPanel.removeAll();
        String selectedChart = (String) chartType.getSelectedItem();
        //Now that the Data is Filtered to User Specification

        //Check each team, and its attributes, on possible filters applied to it
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = filter(NFLData);

        //Looks into sorted data and only puts non-filtered items into the new data map
        //It's time to put the data on the panel in any sorted manner
        //If not sorted, I scrambled the Data so its better looking on the table
        HashMap<String, ArrayList<NFLTeamStatsByYear>> sortedData = new HashMap<>();
        for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> entry : filteredData.entrySet())
            sortedData.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        String selectedSort = (String) sortDropDown.getSelectedItem();
        if (selectedSort != null){
            switch (selectedSort){
                case "Team Name (A-Z)" -> sortedData = sortByNameAsc(sortedData);
                case "Team Name (Z-A)" -> sortedData = sortByNameDec(sortedData);
                case "Year (2003-2023)" -> sortedData = sortByYearAsc(sortedData);
                case "Year (2023-2003)" -> sortedData = sortByYearDec(sortedData);
            }
        }

        //Non filtered teams/seasons are passed to correct method of displaying
        //Since data can be sorted/filtered or Random, it needs to be checked
        if (selectedSort == null)
            displayRandom(sortedData);
        else
            displayInOrder(sortedData);

        revalidate();
        repaint();
        statsPanel.populate(sortedData);
        chartPanel.setMap(sortedData);
        //Flag to determine pie/bar graph displayed
        boolean oneYearFlag = yearFilter.getSelectedItem() != null;
        chartPanel.updateChart(selectedChart, oneYearFlag);
    }

    //Displays Data in its Sorted Way (Which is already sorted in Hash)
    private void displayInOrder (HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData){
        //This Only works for filtered to a team, not a year
        String selectedSort = (String) sortDropDown.getSelectedItem();

        //Year-Based Sorting (Needs to look into ArrayList before teams for each team)
        //Which contradicts itself but possible
        if (selectedSort != null && (selectedSort.equals(SORT_OPTIONS[2]) || selectedSort.equals(SORT_OPTIONS[3]))) {
            //Allows for addition of years as seasons are complete
            //And data is updated
            int minYear = filteredData.values().stream()
                    .flatMap(Collection::stream)
                    .mapToInt(NFLTeamStatsByYear::getYear)
                    .min().orElse(2003);
            int maxYear = filteredData.values().stream()
                    .flatMap(Collection::stream)
                    .mapToInt(NFLTeamStatsByYear::getYear)
                    .max().orElse(2023);

            //A Flag that decides if the year sort is ascend or descend
            //1 or True causes ascending order
            //-1 of False causes descending order
            int yearIncrement = selectedSort.equals(SORT_OPTIONS[2]) ? 1 : -1;

            //Sort Through by getting one year of records for all team before going to next
            //Meaning it goes into each team's hashmap 20 different times
            for (int year = selectedSort.equals(SORT_OPTIONS[2]) ? minYear : maxYear;
                 selectedSort.equals(SORT_OPTIONS[2]) ? year <= maxYear : year >= minYear;
                 year += yearIncrement) {
                for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : filteredData.entrySet()) {
                    for (NFLTeamStatsByYear stats : team.getValue()) {
                        if (stats.getYear() == year) {
                            addTeamYearPanel(team.getKey(), stats);
                        }
                    }
                }
            }
            //Team Sort
        } else {
            for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : filteredData.entrySet()) {
                addTeamYearPanel(team.getKey(), team.getValue().getFirst());
            }
        }
    }

    //Extra Method I implemented to get code to be displayed random
    //If it is not wanting to be sorted by user (Like Initial Start Up)

    private void displayRandom (HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData){
        ArrayList<HashMap<String, NFLTeamStatsByYear>> TeamYearList = new ArrayList<>();

        //Iterate and get everything from OG HashMap
        for(Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : filteredData.entrySet()){
            String teamName = team.getKey();
            ArrayList<NFLTeamStatsByYear> TeamYearStats = team.getValue();

            //New Hashmap that only holds 1 year of records
            for(NFLTeamStatsByYear stats : TeamYearStats){
                HashMap<String, NFLTeamStatsByYear> tempMap = new HashMap<>();
                tempMap.put(teamName, stats);
                TeamYearList.add(tempMap);
            }
        }

        //Shuffle This list to get that shuffled display
        Collections.shuffle(TeamYearList);

        //Iterate and print new shuffled list to display
        for(HashMap<String, NFLTeamStatsByYear> map : TeamYearList) {
            for (Map.Entry<String, NFLTeamStatsByYear> team : map.entrySet()) {
                addTeamYearPanel(team.getKey(), team.getValue());
            }
        }

    }

    // Creates and add the teamYearPanel
    //Which holds the list variation of each data point from the file
    //Which is TeamName (Year of Stats) and a button to view stats in details Panel
    private void addTeamYearPanel(String teamName, NFLTeamStatsByYear stats) {
        //Panel to Hold Each Team-Year Data Point (With Details Button)
        JPanel teamYearPanel = new JPanel();
        teamYearPanel.setLayout(new BoxLayout(teamYearPanel, BoxLayout.X_AXIS));
        teamYearPanel.setPreferredSize(new Dimension(460, 30));
        teamYearPanel.setBackground(new Color(50, 55, 105));

        //Label for name and year of data point
        teamYearPanel.add(Box.createHorizontalGlue());
        JLabel teamYearLabel = new JLabel(teamName + " (" + stats.getYear() + ")");
        teamYearLabel.setForeground(Color.WHITE);
        teamYearPanel.add(teamYearLabel);

        //Button that pushes Team-Year Data to Details Panel
        teamYearLabel.add(Box.createHorizontalGlue());
        JButton detailsButton = new JButton("Details");
        detailsButton.addActionListener(e -> detailsPanel.updateDetails(stats));
        teamYearPanel.add(detailsButton);
        teamYearPanel.add(Box.createHorizontalGlue());

        displayPanel.add(teamYearPanel);
    }
}

