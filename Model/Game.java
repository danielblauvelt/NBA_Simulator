package Model;

/**
 * The interface that allows the game to be played. This interface only has one method
 * that returns an array of ints that represent the score of the game that was played.
 */
public interface Game {

  /**
   * Returns an array of ints representing the score of the game. Calls a method on each team object
   * to get their point totals. Takes into account hte team's defensive ability, as well as
   * the pace that they typically played at in game.
   * @return an int array representing the final score. Team 1's score is at index 0, while
   * team 2;s score is at index 1.
   */
  public int[] playGame();
}
