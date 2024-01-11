package Model;

/**
 * Implementation of the Game interface. This class takes in two teams as fields and uses them
 * to play a game.
 */
public class GameImpl implements Game {

  Team team1;
  Team team2;


  public GameImpl(Team team1, Team team2) {
    this.team1 = team1;
    this.team2 = team2;
  }


  @Override
  public int[] playGame() {
    int[] scores = new int[2];
    this.team1.determineNewFieldGoalPct(this.team2.getOppfgPct());
    this.team2.determineNewFieldGoalPct(this.team1.getOppfgPct());
    int possessions = (int)((this.team1.getPace() + this.team2.getPace()) / 2);

    double[] team1Def = this.team1.getDefStats();
    double[] team2Def = this.team2.getDefStats();
    double foulsTeam1 = this.team1.getFPP();
    double foulsTeam2 = this.team2.getFPP();
    int score1 = this.team1.getPoints(possessions, team2Def[0], team2Def[1], foulsTeam2);
    int score2 = this.team2.getPoints(possessions, team1Def[0], team1Def[1], foulsTeam1);

    while(score1 == score2) {
      score1 += this.team1.getPoints((int) ((double)possessions * (5/48)), team2Def[0], team2Def[1], foulsTeam2);
      score2 += this.team2.getPoints((int) ((double)possessions * (5/48)), team1Def[0], team1Def[1], foulsTeam1);
    }
    scores[0] = score1;
    scores[1] =score2;
    return scores;
  }
}
