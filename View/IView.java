package View;

import java.awt.*;

import Controller.Controller;

/**
 * The graphical user interface for ths program. Allows the user to pick two teams and the play a
 * game between them. It is also responsible for displaying the score, as well as the box score.
 */
public interface IView {

  /**
   * adds the necessary actions listeners. In this case it is the ability to set the two teams
   * and then play a game.
   * @param c the controller that will be called upon when these buttons are pressed.
   */
  public void addFeatures(Controller c);

  /**
   * Displays the score to the user.
   * @param score The final score of the game
   */
  public void displayScore(String score);

  /**
   * Displays the box score to the user in a pop-up window.
   * @param boxScore an array of strings representing all the stats from the game.
   */
  public void createBoxscore(String[] boxScore);
}
