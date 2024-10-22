package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

import static src.vizDataBack.*;

public class TablePanel extends JPanel{
    //Data taken from csv file
    private HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData;
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

    //Constructor
    public TablePanel(DetailsPanel detailsPanel, StatsPanel statsPanel, ChartPanel chartPanel){
        this.detailsPanel = detailsPanel;
        this.statsPanel = statsPanel;
        this.chartPanel = chartPanel;
        NFLData = getTeamsFromCSV("resources/NFLteam_stats_2023.csv");
        //This is needed to reset the data when reverting back from a filter
        //Too deep ;(
        HashMap<String, ArrayList<NFLTeamStatsByYear>> OGNFLData = NFLData;
        setBackground(Color.BLACK);

        //Control Panel to hold filters and sorts
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(500, 50));
        Color brilBlue = new Color(62, 95, 138);
        controlPanel.setBackground(brilBlue);

        //Inside Control Panel
        //Sort Drop Down Menu
        sortDropDown = new JComboBox<>(SORT_OPTIONS);
        sortDropDown.addActionListener(e -> {
            //Four Options to Sort List by
            //Names (A-Z)
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[0]))
                NFLData = sortByNameAsc(NFLData);
            //Names (Z-A)
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[1]))
                NFLData = sortByNameDec(NFLData);
            //Years (2003-2023)
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[2]))
                NFLData = sortByYearAsc(NFLData);
            //Years (2023-2003)
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[3]))
                NFLData = sortByYearDec(NFLData);
            updateDisplay();
        });
        controlPanel.add(sortDropDown);

        //Drop Down for picking a Team
        String[] teamNames = NFLData.keySet().toArray(new String[0]);
        teamFilter = new JComboBox<>(teamNames);
        teamFilter.insertItemAt("All Teams", 0);
        teamFilter.setSelectedIndex(0);

        //Drop Down for picking a Year
        Integer[] yearOfStats = IntStream.rangeClosed(2003, 2023).boxed().toArray(Integer[]::new);
        yearFilter = new JComboBox<>(yearOfStats);
        yearFilter.insertItemAt(null, 0);
        yearFilter.setSelectedIndex(0);
        //Since the user can filter by both at once, the action listener makes sure to see both for either
        ActionListener filterListener = e -> {
            String selectedTeam = (String) teamFilter.getSelectedItem();
            Integer selectedYear =  (Integer) yearFilter.getSelectedItem();
            // Apply filters using vizDataBack methods
            HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = new HashMap<>(NFLData);

            assert selectedTeam != null;
            if (!selectedTeam.equals("All Teams")) {
                filteredData = filterByTeam(filteredData, selectedTeam);
            }
            else
                filteredData = OGNFLData;
            if (selectedYear != null) {
                filteredData = filterByYear(new HashMap<>(filteredData), selectedYear);
            }
            else
                filteredData = OGNFLData;

            NFLData = filteredData;
            updateDisplay();
        };
        teamFilter.addActionListener(filterListener);
        yearFilter.addActionListener(filterListener);

        //Add to control Panel then to Table Panel
        controlPanel.add(teamFilter);
        controlPanel.add(yearFilter);

        //Display Panel (Actual List)
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setBackground(Color.CYAN);
        updateDisplay();

        //Turns displayPanel into a scrollable one
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Set Layout so that the Scrolling Actually Occurs
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 600));
        //Add to table Panel
        add(Box.createVerticalGlue());
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    private boolean isFiltered(Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team){
        String selectedTeam = (String) teamFilter.getSelectedItem();
        Integer selectedYear = (Integer) yearFilter.getSelectedItem();

        String teamName = team.getKey();
        ArrayList<NFLTeamStatsByYear> statsList = team.getValue();

        assert selectedTeam != null;
        if (!selectedTeam.equals("All Teams") && !teamName.equals(selectedTeam)) {
            return true; // Filter by team
        }
        if (selectedYear != null) {
            // Check if any stats for the selected year exist for this team
            return statsList.stream().noneMatch(stats -> stats.getYear() == selectedYear);
        }

        return false;
    }

    private void updateDisplay(){
        displayPanel.removeAll();
        //Trying this
        String selectedSort = (String) sortDropDown.getSelectedItem();

        assert selectedSort != null;
        if (selectedSort.equals(SORT_OPTIONS[2])) { // Year (2003-2023)
            NFLData = sortByYearAsc(NFLData);
            displayByYear();
        } else if (selectedSort.equals(SORT_OPTIONS[3])) { // Year (2023-2003)
            NFLData = sortByYearDec(NFLData);
            displayByYear();
        } else {
            // For sorting by team name, use the existing logic
            for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : NFLData.entrySet()) {
                if (!isFiltered(team)) {
                    for (NFLTeamStatsByYear stats : team.getValue()) {
                        addTeamYearPanel(team.getKey(), stats);
                    }
                }
            }
        }

        revalidate();
        repaint();
    }

    private void displayByYear() {
        //Allows for addition of years as seasons are complete
        //And data is updated
        int minYear = NFLData.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(NFLTeamStatsByYear::getYear)
                .min().orElse(2003);
        int maxYear = NFLData.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(NFLTeamStatsByYear::getYear)
                .max().orElse(2023);

        // Determine the year iteration order
        String selectedSort = (String) sortDropDown.getSelectedItem();
        assert selectedSort != null;
        int yearIncrement = selectedSort.equals(SORT_OPTIONS[2]) ? 1 : -1;
        int initialYear = selectedSort.equals(SORT_OPTIONS[2]) ? minYear : maxYear;

        // Iterate over the years in the determined order
        for (int year = initialYear;
             selectedSort.equals(SORT_OPTIONS[2]) ? year <= maxYear : year >= minYear;
             year += yearIncrement) {

            for (Map.Entry<String, ArrayList<NFLTeamStatsByYear>> team : NFLData.entrySet()) {
                if (!isFiltered(team)) {
                    for (NFLTeamStatsByYear stats : team.getValue()) {
                        if (stats.getYear() == year) {
                            addTeamYearPanel(team.getKey(), stats);
                        }
                    }
                }
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
        //detailsButton.addActionListener(e -> detailsPanel.updateDetails(stats));
        teamYearPanel.add(detailsButton);
        teamYearPanel.add(Box.createHorizontalGlue());

        displayPanel.add(teamYearPanel);
    }
}