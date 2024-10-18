package src;

public class NFLTeam {
    //Details for DetailsPanel
    private final String teamName;
    //Actual Data of Team
    //Game Stats (Calculate Win %)
    private final int wins;
    private final int losses;
    //Points Stats (Calculate score difference)
    private final int points_scored;
    private final int points_allowed;
    //Yards Stats
    private final int totalYards;
    private final int rushing;
    private final int passing;
    //Others (Calculate passing % and total tds)
    private final int turnOvers;
    private final int passes_att;
    private final int passes_cmp;
    private final int passing_tds;
    private final int rushing_tds;
    private final int penalties;

    public NFLTeam(String teamName, int wins, int losses, int pointsScored, int pointsAllowed,
                   int totalYards, int rushYds, int passYds, int turnovers, int passAtt, int passCmp, int passTd, int rushTd, int penalties) {
        this.teamName = teamName;
        this.wins = wins;
        this.losses = losses;
        this.points_scored = pointsScored;
        this.points_allowed = pointsAllowed;
        this.totalYards = totalYards;
        this.rushing = rushYds;
        this.passing = passYds;
        this.turnOvers = turnovers;
        this.passes_att = passAtt;
        this.passes_cmp = passCmp;
        this.passing_tds = passTd;
        this.rushing_tds = rushTd;
        this.penalties = penalties;
    }

    //Getters for Every private variable (Probably used in visuals)
    //No setters as this is data from a file, never to be set by a user, only taken from file.
    String getTeamName() {
        return teamName;
    }
    int getWins() {
        return wins;
    }
    int getLosses() {
        return losses;
    }
    int getPointsScored() {
        return points_scored;
    }
    int getPointsAllowed() {
        return points_allowed;
    }
    int getTotalYards() {
        return totalYards;
    }
    int getRushing() {
        return rushing;
    }
    int getPassing() {
        return passing;
    }
    int getTurnOvers() {
        return turnOvers;
    }
    int getPassAtt() {
        return passes_att;
    }
    int getPassCmp() {
        return passes_cmp;
    }
    int getPassTds() {
        return passing_tds;
    }
    int getRushingTds() {
        return rushing_tds;
    }
    int getPenalties() {
        return penalties;
    }




}

