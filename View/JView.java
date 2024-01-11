package View;

import java.awt.*;

import javax.swing.*;

import Controller.Controller;

/**
 * Implementation of the View interface. Creates all the necessary panels for the GUI.
 */
public class JView extends JFrame implements IView{

  private final JComboBox team1;

  private final JComboBox team2;

  private final JButton play;

  private final JLabel score;


  private JDialog boxscore;



  public JView(String caption) {
    super(caption);
    JPanel mainPanel = new JPanel();
    mainPanel.setSize(new Dimension(500, 500));
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    this.team1 = new JComboBox();
    this.team2 = new JComboBox();
    this.score = new JLabel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    String[] options = {"Atlanta Hawks", "Boston Celtics", "Brooklyn Nets",
            "Charlotte Hornets", "Chicago Bulls", "Cleveland Cavaliers", "Dallas Mavericks",
            "Denver Nuggets", "Detroit Pistons", "Golden State Warriors",
            "Houston Rockets", "Indiana Pacers", "Los Angeles Clippers",
    "Los Angeles Lakers", "Memphis Grizzlies", "Miami Heat", "Milwaukee Bucks", "" +
            "Minnesota Timberwolves", "New Orleans Pelicans", "New York Knicks",
    "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers", "Phoenix Suns",
    "Portland Trail Blazers", "Sacramento Kings", "San Antonio Spurs", "Toronto Raptors",
    "Utah Jazz", "Washington Wizards"};
    this.team1.setActionCommand("team 1");
    this.team2.setActionCommand("team 2");
    this.addOptions(this.team1, options);
    this.addOptions(this.team2, options);
    this.play = new JButton("play game");
    mainPanel.add(this.team1);
    mainPanel.add(this.team2);
    mainPanel.add(this.score);
    mainPanel.add(this.play);
    add(mainPanel);
    pack();
    setVisible(true);
  }

  private void addOptions(JComboBox team, String[] options) {
    for(int i = 0; i < options.length; i++) {
      team.addItem(options[i]);
    }
  }

  @Override
  public void addFeatures(Controller c) {
    this.team1.addActionListener(evt -> c.setTeam1((String) this.team1.getSelectedItem()));
    this.team2.addActionListener(evt -> c.setTeam2((String) this.team2.getSelectedItem()));
    this.play.addActionListener(evt -> c.playGame());
  }

  @Override
  public void displayScore(String score) {
    this.score.setText(score);

  }

  @Override
  public void createBoxscore(String[] boxScore) {
    this.boxscore = new JDialog();
    this.boxscore.setSize(new Dimension(500, 500));
    this.boxscore.setVisible(true);
    JPanel stats = new JPanel();
    stats.setSize(500, 500);
    stats.setVisible(true);
    for(int i = 0; i < boxScore.length; i++) {
      stats.add(new JLabel(System.lineSeparator() + boxScore[i]));
      JSeparator j = new JSeparator();
      j.setOrientation((SwingConstants.HORIZONTAL));
     stats.add(j);
    }
    boxscore.add(stats);
  }
}
