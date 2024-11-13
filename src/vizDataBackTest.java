package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static src.NFLFilters.*; // Import from NFLFilters class

public class vizDataBackTest {

    public static void main(String[] args) {
        // ... (Existing tests) ...

        // Test TeamFilter (adjust to use NFLFilters.TeamFilter)
        AbstractFilter teamFilter = new NFLFilters.TeamFilter("Arizona Cardinals");
        HashMap<String, ArrayList<NFLTeamStatsByYear>> teamData = new HashMap<>();
        teamData.put("Arizona Cardinals", new ArrayList<>());
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredTeamData = teamFilter.filter(teamData);
        if (filteredTeamData.containsKey("Arizona Cardinals")) {
            System.out.println("TeamFilter passed");
        } else {
            System.out.println("Error: TeamFilter failed");
        }

        // Test YearFilter (adjust to use NFLFilters.YearFilter)
        AbstractFilter yearFilter = new NFLFilters.YearFilter(2023);
        HashMap<String, ArrayList<NFLTeamStatsByYear>> yearData = new HashMap<>();
        ArrayList<NFLTeamStatsByYear> yearStatsList = new ArrayList<>();
        yearStatsList.add(new NFLTeamStatsByYear("Arizona Cardinals", 2023, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        yearData.put("Arizona Cardinals", yearStatsList);
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredYearData = yearFilter.filter(yearData);
        if (!filteredYearData.isEmpty()) {
            System.out.println("YearFilter passed");
        } else {
            System.out.println("Error: YearFilter failed");
        }

        // Test WinsFilter (adjust to use NFLFilters.WinsFilter)
        AbstractFilter winsFilter = new NFLFilters.WinsFilter(5);
        HashMap<String, ArrayList<NFLTeamStatsByYear>> winsData = new HashMap<>();
        ArrayList<NFLTeamStatsByYear> winsStatsList = new ArrayList<>();
        winsStatsList.add(new NFLTeamStatsByYear("Arizona Cardinals", 2023, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        winsData.put("Arizona Cardinals", winsStatsList);
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredWinsData = winsFilter.filter(winsData);
        if (!filteredWinsData.isEmpty()) {
            System.out.println("WinsFilter passed");
        } else {
            System.out.println("Error: WinsFilter failed");
        }

        // Test LossesFilter (adjust to use NFLFilters.LossesFilter)
        AbstractFilter lossesFilter = new NFLFilters.LossesFilter(8);
        HashMap<String, ArrayList<NFLTeamStatsByYear>> lossesData = new HashMap<>();
        ArrayList<NFLTeamStatsByYear> lossesStatsList = new ArrayList<>();
        lossesStatsList.add(new NFLTeamStatsByYear("Arizona Cardinals", 2023, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        lossesData.put("Arizona Cardinals", lossesStatsList);
        HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredLossesData = lossesFilter.filter(lossesData);
        if (!filteredLossesData.isEmpty()) {
            System.out.println("LossesFilter passed");
        } else {
            System.out.println("Error: LossesFilter failed");
        }
    }
}