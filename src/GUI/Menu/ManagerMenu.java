package src.GUI.Menu;

import src.GUI.Tabs.AccessPropertiesForm;
import src.GUI.Tabs.MakeReportForm;
import src.GUI.Tabs.ManageFeesForm;
import src.GUI.Tabs.ManageStateListingForm;
import src.GUI.Tabs.ViewUsersWindow;

/**
 * ManagerMenu displays the manager window
 */
public class ManagerMenu extends Menu {

  /**
   * Constructor
   * Launch the application.
   *
   * @param user 	manager's username
   */
  public ManagerMenu(String user) {
    /**
     * Create the frame.
     */
    super("Manager Menu");
    greeting.setText("Welcome " + user);
    this.add(greeting);
    panel1 = new MakeReportForm();
    panel2 = new ManageFeesForm();
    panel3 = new ManageStateListingForm(user, "manager");
    panel4 = new ViewUsersWindow(user);
    panel5 = new AccessPropertiesForm();
    tabs.add("Report", panel1);
    tabs.add("Manage Fees", panel2);
    tabs.add("Manage Listings", panel3);
    tabs.add("Access Users", panel4);
    tabs.add("Access Properties", panel5);
    this.add(tabs);
    this.setLayout(null);
    this.add(logout);
  }
}
