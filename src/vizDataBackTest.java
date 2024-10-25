package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static src.vizDataBack.*;

public class vizDataBackTest {

    public static void main(String[] args) {
        // ... (Existing tests) ...

        // Test teamFilterPredicate
        Predicate<Map.Entry<String, ArrayList<NFLTeamStatsByYear>>> teamPredicate = filterByTeam("Arizona Cardinals");
        if (teamPredicate.test(Map.entry("Arizona Cardinals", new ArrayList<>()))) {
            System.out.println("teamFilterPredicate passed");
        } else {
            System.out.println("Error: teamFilterPredicate failed");
        }

        // Test yearFilterPredicate
        Predicate<Map.Entry<String, ArrayList<NFLTeamStatsByYear>>> yearPredicate = filterByYear(2023);
        ArrayList<NFLTeamStatsByYear> statsList = new ArrayList<>();
        statsList.add(new NFLTeamStatsByYear("Arizona Cardinals", 2023, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        if (yearPredicate.test(Map.entry("Arizona Cardinals", statsList))) {
            System.out.println("yearFilterPredicate passed");
        } else {
            System.out.println("Error: yearFilterPredicate failed");
        }

        // Test winsFilterPredicate
        Predicate<Map.Entry<String, ArrayList<NFLTeamStatsByYear>>> winsPredicate = filterByWins(5);
        statsList.clear();
        statsList.add(new NFLTeamStatsByYear("Arizona Cardinals", 2023, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        if (winsPredicate.test(Map.entry("Arizona Cardinals", statsList))) {
            System.out.println("winsFilterPredicate passed");
        } else {
            System.out.println("Error: winsFilterPredicate failed");
        }

        // Test lossesFilterPredicate
        Predicate<Map.Entry<String, ArrayList<NFLTeamStatsByYear>>> lossesPredicate = filterByLosses(8);
        statsList.clear();
        statsList.add(new NFLTeamStatsByYear("Arizona Cardinals", 2023, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        if (lossesPredicate.test(Map.entry("Arizona Cardinals", statsList))) {
            System.out.println("lossesFilterPredicate passed");
        } else {
            System.out.println("Error: lossesFilterPredicate failed");
        }
    }
}
