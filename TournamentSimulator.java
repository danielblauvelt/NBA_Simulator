import Controller.Controller;
import Controller.ControllerImpl;
import View.IView;
import View.JView;

public class TournamentSimulator {
  public static void main(String[] args) {
    IView view = new JView("Tournament Simulator");
    Controller c = new ControllerImpl();
    c.setView(view);
  }
}
