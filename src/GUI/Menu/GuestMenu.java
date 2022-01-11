package src.GUI.Menu;

import src.GUI.Tabs.GuestSearchPropertyForm;
import src.GUI.Tabs.LoginForm;
import src.GUI.Tabs.RegisterForm;

/**
 * GuestMenu displays the guest window 
 */
public class GuestMenu extends Menu {

  /**
   * Constructor
   */
  public GuestMenu() {
    /**
     * Create the frame.
     */
    super("Start Menu");
    greeting.setText("Rental Property System");
    this.add(greeting);
    panel1 = new LoginForm(this);
    panel2 = new GuestSearchPropertyForm();
    panel3 = new RegisterForm(this);
    tabs.add("Login", panel1);
    tabs.add("Search as Guest", panel2);
    tabs.add("Register", panel3);
    this.add(tabs);
    this.setLayout(null);
  }
}
