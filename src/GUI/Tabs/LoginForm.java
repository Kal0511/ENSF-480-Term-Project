package src.GUI.Tabs;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.User;
import src.GUI.Menu.*;

/**
 * LoginForm builds and displays the login form tab used by a guest user
 */
public class LoginForm extends GUI {

  private Menu menu;
  private JLabel usernameLabel;
  private JTextField usernameText;
  private JLabel passwordLabel;
  private JTextField passwordText;
  private JButton loginButton;
  private JLabel status;

	/**
	 * Constructor
	 * 
	 * @param caller	menu object which calls the constructor
	 */
  public LoginForm(Menu caller) {
    // create the DAO
    super();
    menu = caller;
    usernameLabel = new JLabel("User");
    usernameLabel.setBounds(140, 20, 80, 25);
    add(usernameLabel);

    usernameText = new JTextField(20);
    usernameText.setBounds(230, 20, 165, 25);
    add(usernameText);

    passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(140, 50, 80, 25);
    add(passwordLabel);

    passwordText = new JPasswordField(20);
    passwordText.setBounds(230, 50, 165, 25);
    add(passwordText);

    loginButton = new JButton("Login");
    loginButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String username = usernameText.getText();
            String password = passwordText.getText();
            User user = null;

            if (username == null || username.trim().length() == 0) {
              JOptionPane.showMessageDialog(null, "Username field empty.");
            } else {
              List<User> tempUsers = rentalDAO.searchUsername(username);
              if (tempUsers.size() == 0) {
                JOptionPane.showMessageDialog(
                  null,
                  "Username could not be found."
                );
              } else {
                user = tempUsers.get(0);

                if (password == null || password.length() <= 0) {
                  JOptionPane.showMessageDialog(null, "Password field empty.");
                } else if (user.getPassword().equals(password)) {
                  if (user.getAccountType().equals("renter")) {
                    menu.dispose();
                    RenterMenu frame = new RenterMenu(username);
                    frame.setVisible(true);
                  }
                  if (user.getAccountType().equals("landlord")) {
                    menu.dispose();
                    LandlordMenu frame = new LandlordMenu(username);
                    frame.setVisible(true);
                  }
                  if (user.getAccountType().equals("manager")) {
                    menu.dispose();
                    ManagerMenu frame = new ManagerMenu(username);
                    frame.setVisible(true);
                  }
                } else {
                  JOptionPane.showMessageDialog(null, "Wrong password.");
                }
              }
            }
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              LoginForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );

    loginButton.setBounds(240, 100, 80, 25);
    add(loginButton);

    status = new JLabel("");
    status.setBounds(250, 130, 100, 25);
    add(status);
  }
}
