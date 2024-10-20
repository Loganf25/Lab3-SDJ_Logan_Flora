package src;

import java.util.ArrayList;
import java.util.HashMap;

public class vizDataBackTest {

    public static void main(String[] args) {
        vizDataBack dataBack = new vizDataBack("resources/NFLteam_stats_2023.csv");

        // Test getTeamsFromCSV
        HashMap<String, ArrayList<NFLTeamStatsByYear>> nflData = dataBack.getTeamsFromCSV();
        if (nflData.isEmpty()) {
            System.out.println("Error: HashMap should not be empty");
        } else if (!nflData.containsKey("Arizona Cardinals")) {
            System.out.println("Error: Should contain 'Arizona Cardinals'");
        } else {
            System.out.println("getTeamsFromCSV passed");
        }

        // Test averagePointsScored
        double avgPoints = dataBack.averagePointsScored();
        if (avgPoints <= 0) {
            System.out.println("Error: Average points should be greater than 0");
        } else {
            System.out.println("averagePointsScored passed");
        }

        // Test filterByYear
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredDataByYear = dataBack.filterByYear(2023);
        if (filteredDataByYear.get("Arizona Cardinals").stream().anyMatch(team -> team.getYear() != 2023)) {
            System.out.println("Error: All years for Arizona Cardinals should be 2023");
        } else {
            System.out.println("filterByYear passed");
        }

        // Test filterByTeam
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredDataByTeam = dataBack.filterByTeam("Arizona Cardinals");
        if (!filteredDataByTeam.containsKey("Arizona Cardinals")) {
            System.out.println("Error: Should contain 'Arizona Cardinals'");
        } else if (filteredDataByTeam.size() != 1) {
            System.out.println("Error: Should contain only 'Arizona Cardinals'");
        } else {
            System.out.println("filterByTeam passed");
        }

        // Test sortByNameAsc
        HashMap<String, ArrayList<NFLTeamStatsByYear>> sortedData = dataBack.sortByNameAsc();
        // You'll need to manually check if the teams are sorted alphabetically

        // Test sortByYearAsc
        dataBack.sortByYearAsc("Arizona Cardinals");
        ArrayList<NFLTeamStatsByYear> teamSeasons = dataBack.filterByTeam("Arizona Cardinals").get("Arizona Cardinals");
        // You'll need to manually check if the years are sorted for Arizona Cardinals
    }
}
/*
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;  


public class vizDataBackTest {

    private final vizDataBack dataBack = new vizDataBack("src/NFLteam_stats_2023.csv");

    @Test
    public void testGetTeamsFromCSV() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> nflData = dataBack.getTeamsFromCSV();
        assertFalse(nflData.isEmpty());
        assertTrue(nflData.containsKey("Arizona Cardinals"));
        assertTrue(nflData.get("Arizona Cardinals").get(0) instanceof NFLTeamStatsByYear);
    }

    @Test
    public void testStatisticsMethods() {
        double avgPoints = dataBack.averagePointsScored();
        double avgYards = dataBack.averageTotalYards();
        double avgTurnovers = dataBack.averageTurnovers();

        assertTrue(avgPoints > 0);
        assertTrue(avgYards > 0);
        assertTrue(avgTurnovers > 0);
    }

    @Test
    public void testFilterByYear() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = dataBack.filterByYear(2023);
        assertTrue(filteredData.get("Arizona Cardinals").stream().allMatch(team -> team.getYear() == 2023));
    }

    @Test
    public void testFilterByTeam() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = dataBack.filterByTeam("Arizona Cardinals");
        assertTrue(filteredData.containsKey("Arizona Cardinals"));
        assertEquals(1, filteredData.size());
    }

    @Test
    public void testFilterByWins() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = dataBack.filterByWins(10);
        assertTrue(filteredData.get("New England Patriots").stream().allMatch(team -> team.getWins() >= 10));
    }

    @Test
    public void testFilterByLosses() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = dataBack.filterByLosses(10);
        assertTrue(filteredData.get("Arizona Cardinals").stream().allMatch(team -> team.getLosses() >= 10));
    }

    @Test
    public void testSortByNameAsc() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> sortedData = dataBack.sortByNameAsc();
        List<String> teamNames = new ArrayList<>(sortedData.keySet());
        assertTrue(teamNames.indexOf("Arizona Cardinals") < teamNames.indexOf("Buffalo Bills"));
    }

    @Test
    public void testSortByNameDec() {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> sortedData = dataBack.sortByNameDec();
        List<String> teamNames = new ArrayList<>(sortedData.keySet());
        assertTrue(teamNames.indexOf("Arizona Cardinals") > teamNames.indexOf("Buffalo Bills"));
    }

    @Test
    public void testSortByYearAsc() {
        dataBack.sortByYearAsc("Arizona Cardinals");
        ArrayList<NFLTeamStatsByYear> teamSeasons = dataBack.filterByTeam("Arizona Cardinals").get("Arizona Cardinals");
        assertTrue(teamSeasons.get(0).getYear() < teamSeasons.get(teamSeasons.size() - 1).getYear());
    }

    @Test
    public void testSortByYearDec() {
        dataBack.sortByYearDec("Arizona Cardinals");
        ArrayList<NFLTeamStatsByYear> teamSeasons = dataBack.filterByTeam("Arizona Cardinals").get("Arizona Cardinals");
        assertTrue(teamSeasons.get(0).getYear() > teamSeasons.get(teamSeasons.size() - 1).getYear());
    }
}
*/