package src.GUI.Menu;

import src.GUI.Tabs.SearchPropertyForm;
import src.GUI.Tabs.ViewEmailWindow;
import src.GUI.Tabs.ViewNotificationWindow;
import src.GUI.Tabs.ViewSubscriptionsWindow;

/**
 * RenterMenu displays the renter window
 */
public class RenterMenu extends Menu {

  /**
   * Constructor
   *
   * @param user 	renter's username
   */
  public RenterMenu(String user) {
    /**
     * Create the frame.
     */
    super("Renter Menu");
    greeting.setText("Welcome " + user);
    this.add(greeting);
    panel1 = new SearchPropertyForm(user);
    panel2 = new ViewNotificationWindow(user);
    panel3 = new ViewSubscriptionsWindow();
    panel4 = new ViewEmailWindow(user);

    tabs.add("Search Properties", panel1);
    tabs.add("See Notifications", panel2);
    tabs.add("Manage Subscriptions", panel3);
    tabs.add("View Email Messages", panel4);
    this.add(tabs);
    this.setLayout(null);
    this.add(logout);
  }
}
