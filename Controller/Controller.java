package Controller;

import Model.Team;
import View.IView;

/**
 * The controller for the simulator. This interface is responsible for storing which two teams
 * will be playing, playing the game when the user chooses to play,
 * and exiting the simulator when necessary.
 */
public interface Controller {

  /**
   * Method that allows user to exist program.
   */
  public void exit();

  /**
   * Method to help generate the gui for the given view. Adds all of the necessary
   * action listeners.
   * @param view the given view that will be updated
   * @throws IllegalArgumentException if view is null
   */
  public void setView(IView view) throws IllegalArgumentException;

  /**
   * Helper method for the playGame method, allows the controller to retrieve the statistics
   * from the team_stats csv file for the given String team-name.
   * @param team1
   */
  public void setTeam1(String team1);

  /**
   * Operates exactly like setTeam1 except for the second team.
   * @param team2
   */
  public void setTeam2(String team2);

  /**
   * Calls upon this game to play a game between the two teams represented by
   * the strings team1 and team2, the result of the game will be displayed in the view.
   */
  public void playGame();


  /**
   * Takes in the statistics that were tracked after playing each game, and displays
   * a box score containing all  the teams basic stats
   * @param finalScore the final score of the game in string form
   * @param team1 the Team object for the first tea, this allows te controller access
   *              to all the team's stats
   * @param team2 the object for the second team
   * @return an array of strings containing the team names, final score, as well
   * as all the basic counting stats from the game
   */
  public String[] generateBoxscore(String finalScore, Team team1, Team team2);
}
