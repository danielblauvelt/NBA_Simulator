package Model;

import java.util.ArrayList;
import java.util.Random;

/**Class for Team. This class takes in all the attributes for a given team, which are assigned
 * in the constructor. This class also sets each of the team's counting stats to 0 at the beginning,
 * and then updates them throughout the game. The main purpose of this class is to get the score
 * of a given team after they play a game, and determine it's counting stats. All of these numbers
 * are then reset to 0 after the game is played.
 *
 */
public class Team {

  private final String teamName;

  private  double threePct;
  private  double fgPct;

  private final double ftPct;

  private final double twoPtAstPer;

  private final double threePtAstPer;

  private final double blkPer;
  private final double stlPer;
  private final double oppThreePt;

  private final double oppTwoPt;
  private final double pace;
  private final double ftRate;
  private final double threeRate;

  private final double tovPct;

  private final double offRebPct;

  private final double fpp;

  private final double threePossessions;

  private int fgs;
  private  int fgAttempts;

  private int threePts;
  private int threeAttempts;

  private int fts;
  private int ftAttempts;

  private int rebounds;
  private int assists;

  private int turnovers;
  private int orb;
  private int steals;
  private int blocks;
  private int fouls;
  private int totalFouls;




  public Team(String teamName, double threePct, double fgPct, double ftPct,
              double twoPtAstPer, double threePtAstPer, double blkPer, double stlPer,
              double oppThreePt, double oppTwoPt,
              double pace, double ftRate,
              double threeRate, double tovPct, double offRebPct, double fpp) {
    this.teamName = teamName;
    this.threePct = threePct;
    this.fgPct = fgPct;
    this.ftPct = ftPct;
    this.twoPtAstPer = twoPtAstPer;
    this.threePtAstPer = threePtAstPer;
    this.blkPer = blkPer;
    this.stlPer = stlPer;
    this.oppThreePt = oppThreePt;
    this.oppTwoPt = oppTwoPt;
    this.pace = pace;
    this.ftRate = ftRate;
    this.threeRate = threeRate;
    this.tovPct = tovPct / 100;
    this.offRebPct = offRebPct / 100;
    this.fpp = fpp;
    this.threePossessions = (1.0 - this.fpp - this.tovPct) * this.threeRate;
    this.fgs = 0;
    this.fgAttempts = 0;
    this.threePts = 0;
    this.threeAttempts = 0;
    this.fts = 0;
    this.ftAttempts = 0;
    this.rebounds = 0;
    this.assists = 0;
    this.steals = 0;
    this.blocks = 0;
    this.turnovers = 0;
    this.fouls = 0;
    this.totalFouls = 0;
  }

  /**
   * Resets all the counting stats to 0.
   */
  public void reset() {
    this.fgs = 0;
    this.fgAttempts = 0;
    this.threePts = 0;
    this.threeAttempts = 0;
    this.fts = 0;
    this.ftAttempts = 0;
    this.rebounds = 0;
    this.orb = 0;
    this.assists = 0;
    this.steals = 0;
    this.blocks = 0;
    this.turnovers = 0;
    this.fouls = 0;
    this.totalFouls = 0;
  }


  /**
   * returns the team name.
   * @return ^^
   */
  public String getTeamName() {
    return this.teamName;
  }

  /**
   * Returns the percentage of possessions that ended in a foul for this team
   * @return ^^
   */
  public double getFPP(){
    return this.fpp;
  }

  /**
   * returns the 2pt and 3pt fg percentages that this team held their opponents to.
   * @return ^^
   */
  public double[] getOppfgPct() {
    return new double[]{this.oppTwoPt, this.oppThreePt};
  }

  /**
   * Returns the average number of possessions that this team had per game
   * @return ^^
   */
    public double getPace() {
    return this.pace;
  }

  /**
   * returns the block and steal percentage for this team.
   * @return ^^
   */
  public double[] getDefStats() {
    return new double[]{this.blkPer, this.stlPer};
  }

  /**
   * Returns an array of all of this teams offensive counting stats which is then used to generate
   * the box score.
   * @return ^^
   */
  public int[] getTeamNumbers() {
    return new int[]{this.fgs, this.fgAttempts, this.threePts, this.threeAttempts,
    this.fts, this.ftAttempts, this.assists, this.orb, this.turnovers};
  }

  /**
   * Returns the opponent's defensive stats, which are also used for the boxscore.
   * @return ^^
   */
  public int[] getOppNumbers() {
    return new int[]{this.rebounds, this.blocks, this.steals,
    this.totalFouls};
  }

  /**
   * Determines a new field goal percentage for this team, based off of the fg pct
   * that their opponent held their opponents to in the regular season.
   * @param oppFgPct fg pct
   *    * that their opponent held their opponents to in the regular season
   */
  public void determineNewFieldGoalPct(double[] oppFgPct) {
    this.fgPct = (oppFgPct[0] + this.fgPct)/2;
    this.threePct = (oppFgPct[1] + this.threePct)/2;
  }

  /**
   * Gets the number of points that this team scored in a game.
   * @param possessions the number of possessions in the game
   * @param oppBlockPer the percentage of possessions for their opponent that end in a block.
   * @param oppStealPer the percentage of possessions for their opponent that end in a steal
   * @param fpp the percentage of possessions for their opponent that end in a foul.
   * @return the number of points scored by this team in a game.
   */
  public int getPoints(int possessions, double oppBlockPer, double oppStealPer, double fpp) {
    int score = 0;
    for(int i = 0; i < possessions; i++) {
      if(i == possessions / 4 || i == possessions / 2 || i == (possessions * 3) / 4) {
        this.fouls = 0;
      }
      int shotInfo = this.shotIn(this.fgPct, this.threePct, oppBlockPer, oppStealPer, fpp);
      score += shotInfo;

    }

    return score;
  }

  private int getFreeThrows(int attempts, double fgPct,
                            double thrPtPct, double oppBlockPer, double oppStealPer,
                            double fpp) {
    this.ftAttempts += attempts;

    double orb = new Random().nextDouble();
    int score = 0;
    for(int i = 0; i < attempts - 1; i++) {
      double madeAttempt = new Random().nextDouble();

      if(madeAttempt <= this.ftPct) {
        score+= 1;
        this.fts += 1;
      }
    }
    if(new Random().nextDouble() <= this.ftPct) {
      this.fts += 1;
      return score + 1;
    }
    if(orb <= 0.139) {
      this.orb += 1;
      return this.shotIn(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
    }
    this.rebounds+= 1;
    return score;
  }

  private int shotIn(double fgPct, double thrPtPct, double oppBlockPer, double oppStealPer,
                       double fpp) {
   double possession = new Random().nextDouble();
   if(possession <= this.tovPct) {
     return this.turnover(oppStealPer);
   }
   else if(possession <= this.tovPct + this.fpp) {
     return this.nonShootingFoul(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
   }
   else if(possession <= this.tovPct + this.fpp + this.threePossessions) {
     return this.takeThree(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
   }
    return this.takeTwo(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
  }

  private int turnover(double oppStealPer) {
    this.turnovers += 1;
    this.updateSteals(oppStealPer);
    return 0;
  }


  private int nonShootingFoul(double fgPct, double thrPtPct, double oppBlockPer, double oppStealPer,
                              double fpp) {
      this.totalFouls += 1;
      this.fouls += 1;
      if (this.fouls >= 5) {
        return this.getFreeThrows(2, fgPct, thrPtPct, oppBlockPer,
                oppStealPer, fpp);
      }

    return this.shotIn(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp/2);
  }

  private int takeThree(double fgPct, double thrPtPct, double oppBlockPer, double oppStealPer,
                        double fpp) {
      double fouledOnThree = new Random().nextDouble();
      double three = new Random().nextDouble();
    this.threeAttempts += 1;
    this.fgAttempts += 1;
      if(three <= thrPtPct) {
        this.getAssist(this.threePtAstPer);
        this.fgs += 1;
        this.threePts += 1;
        if(fouledOnThree <= 0.009) {
          this.fouls +=1;
          this.totalFouls += 1;
          return this.getFreeThrows(3, fgPct, thrPtPct, oppBlockPer,
                  oppStealPer, fpp);
        }

        return 3;
      }
      else if(fouledOnThree <= 0.017) {
          this.fouls += 1;
          this.totalFouls += 1;
          this.fgs += 1;
          return 3 + this.getFreeThrows(1, fgPct, thrPtPct, oppBlockPer,
                  oppStealPer, fpp);
      }
    this.updateBlocks(oppBlockPer);

    return this.offensiveRebound(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
  }

  private int takeTwo(double fgPct, double thrPtPct, double oppBlockPer, double oppStealPer,
                      double fpp) {
    double two = new Random().nextDouble();
    double twoFoul = new Random().nextDouble();
    this.fgAttempts += 1;
    if(two <= fgPct) {
      this.getAssist(this.twoPtAstPer);
      this.fgs += 1;
      if(twoFoul <= 0.07) {
        this.fouls += 1;
        this.totalFouls += 1;
        return 2 + this.getFreeThrows(1, fgPct, thrPtPct, oppBlockPer,
                oppStealPer, fpp);
      }
      return 2;
    }
    if(twoFoul <= 0.139) {
      this.fouls += 1;
      this.totalFouls += 1;
      return this.getFreeThrows(2, fgPct, thrPtPct, oppBlockPer,
              oppStealPer, fpp);
    }
    return this.offensiveRebound(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
  }


  private int offensiveRebound(double fgPct, double thrPtPct, double oppBlockPer, double oppStealPer,
                               double fpp) {
    double offReb = new Random().nextDouble();
    if(offReb <= this.offRebPct) {
      this.orb += 1;
      return this.shotIn(fgPct, thrPtPct, oppBlockPer, oppStealPer, fpp);
    }
    this.updateBlocks(oppBlockPer);
    this.rebounds += 1;
    return 0;
  }
  private void updateSteals(double stealPer) {
    double steal = new Random().nextInt(100);
    if(steal <= this.stlPer * 100) {
      this.steals += 1;
    }
  }

  private void updateBlocks(double blocksPer) {
    double block = new Random().nextInt(100);
    if(block <= this.blkPer * 100) {
      this.blocks += 1;
    }
  }

  private void getAssist(double percentage) {
    int assist = new Random().nextInt(100);

    if(assist <= percentage * 100) {
      this.assists += 1;
    }
  }
}
