package src;

import java.util.*;

public interface DataObserver {
    void update(HashMap<String, ArrayList<NFLTeamStatsByYear>> data, String panelType, NFLTeamStatsByYear selectedStats);
}
