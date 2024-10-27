package src;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//If these imports do NOT work
//Head to the README file for steps to fix
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//The Data Collection side of the Program
public class vizDataBack {
    //This method is the CVSFile Reader responsible for populating the HashMap
    //HashMap used as many years of data has been collected
    //HashMap stores the name of the team as a key, and an ArrayList as the value
    //The arrayList will hold NFLTeamStatsByYear Objects for each season of the 20 season for a given team
    //NFLTeamStats Object holds the actual stats of that year for a given team
    public static HashMap<String, ArrayList<NFLTeamStatsByYear>> getTeamsFromCSV(String fileName) {
        HashMap<String, ArrayList<NFLTeamStatsByYear>> teamsData = new HashMap<>();

        try(FileReader reader = new FileReader(fileName);
            CSVParser csvParser = new CSVParser(reader,  CSVFormat.Builder.create().setHeader().build());){

            //Csv Record is the entire file
            for (CSVRecord csvRecord : csvParser)
            {
                //Based on the headers, it creates variables of each type to be stored in Team
                String teamName = csvRecord.get("team");
                int year = Integer.parseInt(csvRecord.get("year"));
                int wins = Integer.parseInt(csvRecord.get("wins"));
                int losses = Integer.parseInt(csvRecord.get("losses"));
                int pointsScored = Integer.parseInt(csvRecord.get("points"));
                int pointsAllowed = Integer.parseInt(csvRecord.get("points_opp"));
                int totalYards = Integer.parseInt(csvRecord.get("total_yards"));
                int rushYds = Integer.parseInt(csvRecord.get("rush_yds"));
                int passYds = Integer.parseInt(csvRecord.get("pass_yds"));
                int turnovers = Integer.parseInt(csvRecord.get("turnovers"));
                int passAtt = Integer.parseInt(csvRecord.get("pass_att"));
                int passCmp = Integer.parseInt(csvRecord.get("pass_cmp"));
                int passTd = Integer.parseInt(csvRecord.get("pass_td"));
                int rushTd = Integer.parseInt(csvRecord.get("rush_td"));
                int penalties = Integer.parseInt(csvRecord.get("penalties"));

                //Create that new Team and add their stats
                NFLTeamStatsByYear team = new NFLTeamStatsByYear(teamName, year, wins, losses, pointsScored, pointsAllowed,
                        totalYards, rushYds, passYds, turnovers, passAtt, passCmp, passTd, rushTd, penalties);
                //Add that team to the ArrayList of other teams
                teamsData.computeIfAbsent(teamName, k -> new ArrayList<>()).add(team);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return teamsData;
    }


    //Statistics Methods (Used Streams Here)
    //These statistics will display for the selected dataset regardless of what it is
    //Displays for all teams during all years, and all teams during 1 year
    //And 1 team during all years, and 1 team during 1 year.
    //OFC being meaningless if 1 team-1 year (As its averages)

    //Finds average points scored of the data set (One of the above scenarios)
    public static double averagePointsScored(HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        //Had to find a new variable type with an averaging function
        //As the data size can vary based on applied filters
        //Java provides a stats int to do so!!
        IntSummaryStatistics stats = NFLData.values().stream()
                .flatMapToInt(teams -> teams.stream().mapToInt(NFLTeamStatsByYear::getPointsScored))
                .summaryStatistics();
        return stats.getAverage();
    }
    //Almost Identical To the One above, just checking totalYards instead
    public static double averageTotalYards (HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        IntSummaryStatistics stats = NFLData.values().stream()
                .flatMapToInt(teams -> teams.stream().mapToInt(NFLTeamStatsByYear::getTotalYards))
                .summaryStatistics();
        return stats.getAverage();
    }
    //Identical except looking for turnovers
    public static double averageTurnovers (HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        IntSummaryStatistics stats = NFLData.values().stream()
                .flatMapToInt(teams -> teams.stream().mapToInt(NFLTeamStatsByYear::getTurnOvers))
                .summaryStatistics();
        return stats.getAverage();
    }


    //Filterers (Utilized Streams Here)
    //Filter by Year Method (Will only add year chosen)
    public static Predicate<NFLTeamStatsByYear> filterByYear(Integer year) {
        return seasonStats -> year == null || Objects.equals(seasonStats.getYear(), year);
    }

    //Filter by Team Method (Will only add teams chosen)
    public static Predicate<Map.Entry<String, ArrayList<NFLTeamStatsByYear>>> filterByTeam(String teamName) {
        //As the name of the team is the key of the hashmap, it is much simpler than year
        return filt -> teamName.equals("All Teams") || filt.getKey().equals(teamName);
    }

    //Filter by Wins Method (User Inputs Min Wins to be added)
    public static Predicate<NFLTeamStatsByYear> filterByWins (Integer minWins) {
        return seasonStats -> seasonStats.getWins() >= minWins;
    }

    //Filter by Losses Method (User inputs min Losses to be added)
    public static Predicate<NFLTeamStatsByYear> filterByLosses (Integer minLosses) {
        return seasonStats -> seasonStats.getLosses() >= minLosses;
    }

    //Sorters (Name and Year (plus Reversed))
    //A-Z
    //Returns a newly sorted Hashmap
    public static HashMap<String, ArrayList<NFLTeamStatsByYear>> sortByNameAsc(HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData) {
        return NFLData.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    //Z-A
    //Returns a newly sorted Hashmap
    public static HashMap<String, ArrayList<NFLTeamStatsByYear>> sortByNameDec(HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        return NFLData.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    //2003-2024
    //No return as it sorts the years inside the ArrayList of each team, not dealing with entire Hashmap
    public static HashMap<String, ArrayList<NFLTeamStatsByYear>> sortByYearAsc(HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        List<NFLTeamStatsByYear> allTnYStats = NFLData.values().stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(NFLTeamStatsByYear::getYear))
                .toList();
        NFLData = allTnYStats.stream().collect(Collectors.groupingBy(NFLTeamStatsByYear::getTeamName,
                LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
        return NFLData;
    }

    //2023-2003
    //To return for same reason
    public static HashMap<String, ArrayList<NFLTeamStatsByYear>> sortByYearDec(HashMap<String, ArrayList<NFLTeamStatsByYear>> NFLData){
        List<NFLTeamStatsByYear> allTnYStats = NFLData.values().stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(NFLTeamStatsByYear::getYear).reversed())
                .toList();
        NFLData = allTnYStats.stream().collect(Collectors.groupingBy(NFLTeamStatsByYear::getTeamName,
                LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
        return NFLData;
    }
}

