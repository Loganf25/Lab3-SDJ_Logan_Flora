package src;

import java.util.ArrayList;
import java.util.HashMap;

import static src.vizDataBack.*;


public class NFLFilters {
    public abstract static class AbstractFilter{
        public HashMap<String, ArrayList<NFLTeamStatsByYear>> filter(HashMap<String, ArrayList<NFLTeamStatsByYear>> data){
            HashMap<String, ArrayList<NFLTeamStatsByYear>> filteredData = new HashMap<>();
            //Iterates Through Key (Teams)
            for(HashMap.Entry<String, ArrayList<NFLTeamStatsByYear>> team : data.entrySet()){
                //Stores Filtered Stats in Value (ArrayList) (Stats)
                ArrayList<NFLTeamStatsByYear> filteredStats = new ArrayList<>();
                //Iterates through all seasons Stats Object
                for(NFLTeamStatsByYear teamStats : team.getValue()){
                    //Needs to pass all filters in order to get placed into new array
                    if (applyFilter(teamStats))
                        filteredStats.add(teamStats);
                }
                //If that team is alo filtered in, that team, and the new Array list
                //Are added to new Hashmap
                if(!filteredStats.isEmpty())
                    filteredData.put(team.getKey(), filteredStats);
            }
            return filteredData;
        }
        protected abstract boolean applyFilter(NFLTeamStatsByYear teamStats);
    }

    // Concrete Filter Classes
    public static class TeamFilter extends AbstractFilter {
        private final String selectedTeam;

        public TeamFilter(String selectedTeam) {
            this.selectedTeam = selectedTeam;
        }

        @Override
        protected boolean applyFilter(NFLTeamStatsByYear teamStats) {
            return selectedTeam.equals("All Teams") || teamStats.getTeamName().equals(selectedTeam);
        }
    }

    public static class YearFilter extends AbstractFilter {
        private final Integer selectedYear;

        public YearFilter(Integer selectedYear) {
            this.selectedYear = selectedYear;
        }

        @Override
        protected boolean applyFilter(NFLTeamStatsByYear teamStats) {
            return selectedYear == null || teamStats.getYear().equals(selectedYear);
        }
    }

    public static class WinsFilter extends AbstractFilter {
        private final int minWins;

        public WinsFilter(int minWins) {
            this.minWins = minWins;
        }

        @Override
        protected boolean applyFilter(NFLTeamStatsByYear teamStats) {
            return teamStats.getWins() >= minWins;
        }
    }

    public static class LossesFilter extends AbstractFilter {
        private final int minLosses;

        public LossesFilter(int minLosses) {
            this.minLosses = minLosses;
        }

        @Override
        protected boolean applyFilter(NFLTeamStatsByYear teamStats) {
            return teamStats.getLosses() >= minLosses;
        }
    }
}
