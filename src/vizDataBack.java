package src;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//The Data Collection side of the Program
public class vizDataBack {

    //An edited version of prof fileReader/parser to work with CVS files, as that's how my data is stored
    public class CSVFileReader {
        private String fileName;

        public CSVFileReader (String fileName){
            this.fileName = fileName;
        }

        public ArrayList<NFLTeam> getTeams() {
            ArrayList<NFLTeam> teams = new ArrayList<>();

            try(FileReader reader = new FileReader(fileName);
                CSVParser csvParser = new CSVParser(reader,  CSVFormat.Builder.create().setHeader().build())){

                for (CSVRecord csvRecord : csvParser)
                {
                    String teamName = csvRecord.get("team");
                    int wins = Integer.parseInt(csvRecord.get("wins"));
                    int losses = Integer.parseInt(csvRecord.get("losses"));
                    int pointsScored = Integer.parseInt(csvRecord.get("points_scored"));
                    int pointsAllowed = Integer.parseInt(csvRecord.get("points_allowed"));
                    int totalYards = Integer.parseInt(csvRecord.get("total_yards"));
                    int rushYds = Integer.parseInt(csvRecord.get("rush_yds"));
                    int passYds = Integer.parseInt(csvRecord.get("pass_yds"));
                    int turnovers = Integer.parseInt(csvRecord.get("turnovers"));
                    int passAtt = Integer.parseInt(csvRecord.get("pass_att"));
                    int passCmp = Integer.parseInt(csvRecord.get("pass_cmp"));
                    int passTd = Integer.parseInt(csvRecord.get("pass_td"));
                    int rushTd = Integer.parseInt(csvRecord.get("rush_td"));
                    int penalties = Integer.parseInt(csvRecord.get("penalties"));
                    NFLTeam team = new NFLTeam(teamName, wins, losses, pointsScored, pointsAllowed,
                            totalYards, rushYds, passYds, turnovers, passAtt, passCmp, passTd, rushTd, penalties);
                    teams.add(team);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            return teams;
        }
    }

}

