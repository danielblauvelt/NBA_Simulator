package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import Model.Game;
import Model.GameImpl;
import Model.Team;
import View.IView;


/**
 * Implementation of the Controller interface. This class
 * inherits all  the public methods from the interface, and also utilizes
 * several private methods. This class has the ability to read a csv file, which
 * it utilizes in this case to assign attributes to each nba team.
 */
public class ControllerImpl implements Controller {

  HashMap<String, Team> teams;

  IView v;
  String team1;
  String team2;

  Game g;

  public ControllerImpl() {
    this.teams = new HashMap<String, Team>();
    this.readFile("team_stats.csv");
    this.team1 = "";
    this.team2 = "";
  }


  private void readFile(String text) {
    Path pathToFile = Paths.get(text);

    try (BufferedReader br = Files.newBufferedReader(pathToFile,
            StandardCharsets.UTF_8)) {

      String line = br.readLine();
      line = br.readLine();

      while(line != null) {
        String[] attributes = line.split(",");
        if(attributes[0].equals("")) {
          break;
        }
          if(attributes[0].contains("*")) {
            attributes[0] = attributes[0].substring(0, attributes[0].length() - 1);
          }
          teams.put(attributes[0], this.createTeam(attributes));
            line = br.readLine();
        }
      }

    catch(IOException e) {
      e.printStackTrace();
    }
  }

  private Team createTeam(String[] attributes) {
    double[] stats = new double[attributes.length];
    for(int i = 1; i < attributes.length; i++) {
      stats[i] = Double.parseDouble(attributes[i]);
    }

    return new Team(attributes[0], stats[1], stats[2],
            stats[3], stats[4], stats[5], stats[6],
            stats[7], stats[8], stats[9], stats[10], stats[11], stats[12], stats[13], stats[14],
            stats[15]);
  }

  @Override
  public void exit() {
    System.exit(0);
  }

  @Override
  public void setView(IView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View must be non null!");
    }
    this.v = view;
    this.v.addFeatures(this);
  }

  @Override
  public void setTeam1(String team1) {
    this.team1 = team1;
  }

  @Override
  public void setTeam2(String team2) {
    this.team2 = team2;
  }

  @Override
  public void playGame() {
    Team team1 = this.teams.get(this.team1);
    Team team2 = this.teams.get(this.team2);
    team1.reset();
    team2.reset();
    this.g = new GameImpl(team1, team2);
    int[] score = this.g.playGame();
    String finalScore = score[0] + "-" + score[1];
    this.v.displayScore(finalScore);
    String[] box = this.generateBoxscore(finalScore, team1, team2);
    this.v.createBoxscore(box);
  }

  @Override
  public String[] generateBoxscore(String finalScore, Team team1, Team team2) {
    int[] team1Off = team1.getTeamNumbers();
    int[] team1Def = team2.getOppNumbers();
    int[] team2Off = team2.getTeamNumbers();
    int[] team2Def = team1.getOppNumbers();
    int team1Reb = team1Def[0] + team1Off[7];
    int team2Reb = team2Def[0] + team2Off[7];
    String names = team1.getTeamName() + "    " + team2.getTeamName();
     String fg =   team1Off[0] + "/" + team1Off[1] + "  fg%  " + team2Off[0] + "/" + team2Off[1];
    String three = team1Off[2] + "/" + team1Off[3] + "  3pt%  "
    + team2Off[2] + "/" + team2Off[3];
    String fts = team1Off[4] + "/" + team1Off[5] +
          "  ft%  "
            + team2Off[4] + "/" + team2Off[5];
    String ast = team1Off[6] + "  ast  "
            + team2Off[6];
    String reb = team1Reb + "  reb  "
            + team2Reb;
    String orb = team1Off[7] + "  orb  "
            + team2Off[7];
    String blks = team1Def[1] + "  blks  "
            + team2Def[1];
    String stls = team1Def[2] + "  stls  "
            + team2Def[2];
    String tov = team1Off[8] + "  tov  "
            + team2Off[8];
    String fouls = team1Def[3] + "  fouls  "
            + team2Def[3];
    return new String[]{names, finalScore, fg, three, fts, ast, reb, orb, blks, stls, tov, fouls};
  }
}
