package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static
src.vizDataBack.*;

public class vizDataBackTest {

    public static void main(String[] args) {
        // ... (Existing tests) ...

        // Test teamFilterPredicate (remains the same)
        Predicate<Map.Entry<String, ArrayList<NFLTeamStatsByYear>>> teamPredicate = filterByTeam("Arizona Cardinals");
        if (teamPredicate.test(Map.entry("Arizona Cardinals", new ArrayList<>()))) {
            System.out.println("teamFilterPredicate passed");
        } else {
            System.out.println("Error: teamFilterPredicate failed");
        }

        // Test yearFilterPredicate (now uses NFLTeamStatsByYear as input)
        Predicate<NFLTeamStatsByYear> yearPredicate = filterByYear(2023);
        NFLTeamStatsByYear stats = new NFLTeamStatsByYear("Arizona Cardinals", 2023, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        if (yearPredicate.test(stats)) {
            System.out.println("yearFilterPredicate passed");
        } else {
            System.out.println("Error: yearFilterPredicate failed");
        }

        // Test winsFilterPredicate (now uses NFLTeamStatsByYear as input)
        Predicate<NFLTeamStatsByYear> winsPredicate = filterByWins(5);
        stats = new NFLTeamStatsByYear("Arizona Cardinals", 2023, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        if (winsPredicate.test(stats)) {
            System.out.println("winsFilterPredicate passed");
        } else {
            System.out.println("Error: winsFilterPredicate failed");
        }

        // Test lossesFilterPredicate (remains the same)
        Predicate<NFLTeamStatsByYear> lossesPredicate = filterByLosses(8);
        stats = new NFLTeamStatsByYear("Arizona Cardinals", 2023, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        if (lossesPredicate.test(stats)) {
            System.out.println("lossesFilterPredicate passed");
        } else {
            System.out.println("Error: lossesFilterPredicate failed");
        }
    }
}