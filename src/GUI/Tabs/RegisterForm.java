package src.GUI.Tabs;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.User;
import src.GUI.Menu.*;

/**
 * RegisterForm builds and displays the registration tab used by a guest to register for an account.
 */
public class RegisterForm extends GUI {

  private Menu menu;

  private JLabel usernameLabel;
  private JTextField usernameText;
  private JLabel passwordLabel;
  private JTextField passwordText;
  private JLabel confirmPasswordLabel;
  private JTextField confirmPasswordText;
  private JLabel accountTypeLabel;
  private JComboBox<String> accountTypeBox;
  private JButton registerButton;
  private JLabel status;

	/**
	 * Constructor
	 * 
	 * @param caller	menu object which calls the constructor
	 */
  public RegisterForm(Menu caller) {
    // create the DAO
    super();
    menu = caller;
    accountTypeLabel = new JLabel("AccountType:");
    accountTypeLabel.setBounds(130, 20, 80, 25);
    add(accountTypeLabel);

    String[] types = { "Renter", "Landlord", "Manager" };
    accountTypeBox = new JComboBox<String>(types);
    accountTypeBox.setBounds(240, 20, 100, 25);
    add(accountTypeBox);

    usernameLabel = new JLabel("User");
    usernameLabel.setBounds(130, 50, 80, 25);
    add(usernameLabel);

    usernameText = new JTextField(20);
    usernameText.setBounds(240, 50, 165, 25);
    add(usernameText);

    passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(130, 80, 80, 25);
    add(passwordLabel);

    passwordText = new JPasswordField(20);
    passwordText.setBounds(240, 80, 165, 25);
    add(passwordText);

    confirmPasswordLabel = new JLabel("Confirm Password");
    confirmPasswordLabel.setBounds(130, 110, 80, 25);
    add(confirmPasswordLabel);

    confirmPasswordText = new JPasswordField(20);
    confirmPasswordText.setBounds(240, 110, 165, 25);
    add(confirmPasswordText);

    registerButton = new JButton("Register");
    registerButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String username = usernameText.getText();
            String password = passwordText.getText();
            String confirmPassword = confirmPasswordText.getText();
            String accounttype = accountTypeBox
              .getSelectedItem()
              .toString()
              .toLowerCase();
            if (username == null || username.trim().length() == 0) {
              JOptionPane.showMessageDialog(null, "Username field empty.");
            } else {
              List<User> tempUsers = rentalDAO.searchUsername(username);
              if (tempUsers.size() != 0) {
                JOptionPane.showMessageDialog(null, "Username is taken.");
              } else {
                if (password == null || password.length() == 0) {
                  JOptionPane.showMessageDialog(null, "Password is empty.");
                } else if (password.trim().length() != 0) {
                  if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(
                      null,
                      "Passwords do not match."
                    );
                  } else {
                    rentalDAO.enterUser(
                      new User(username, password, accounttype)
                    );
                    JOptionPane.showMessageDialog(
                      null,
                      "User Successfully Entered! Please Login."
                    );
                    menu.dispose();
                    GuestMenu frame = new GuestMenu();
                    frame.setVisible(true);
                  }
                } else {
                  JOptionPane.showMessageDialog(
                    null,
                    "Password cannot be only whitespace."
                  );
                }
              }
            }
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              RegisterForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );

    registerButton.setBounds(210, 160, 100, 25);
    add(registerButton);

    status = new JLabel("");
    status.setBounds(100, 200, 100, 25);
    add(status);
  }
}
