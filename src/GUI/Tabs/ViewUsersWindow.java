package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * ViewUsersWindow builds and displays the user viewer tab used to view all users in the database.
 */
public class ViewUsersWindow extends GUI {

  private JButton viewUsersButton;
  private JButton deleteUserButton;
  private JScrollPane scrollPane;
  private JTable userTable;

  /**
   * Constructor
   */
  public ViewUsersWindow(String user) {
    super();
    viewUsersButton = new JButton("Show Users");
    viewUsersButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          List<User> users = null;
          try {
            deleteUserButton.setVisible(true);
            viewUsersButton.setText("Refresh Users");
            users = rentalDAO.getAllUsers();
            UserTableModel model = new UserTableModel(users);
            userTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewUsersWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    viewUsersButton.setBounds(200, 40, 130, 25);
    add(viewUsersButton);
    deleteUserButton = new JButton("Delete User");
    deleteUserButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String username = String.valueOf(
            userTable.getValueAt(userTable.getSelectedRow(), 0)
          );
          if (username.equals(user)) {
            JOptionPane.showMessageDialog(null, "You cannot delete yourself!");
          } else {
            try {
              rentalDAO.deleteUser(username);
              JOptionPane.showMessageDialog(
                null,
                "Refresh to show user deleted."
              );
            } catch (Exception exc) {
              JOptionPane.showMessageDialog(
                ViewUsersWindow.this,
                "Error: " + exc,
                "Error",
                JOptionPane.ERROR_MESSAGE
              );
            }
          }
        }
      }
    );
    deleteUserButton.setBounds(200, 350, 150, 25);
    deleteUserButton.setVisible(false);
    add(deleteUserButton);
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 90, 500, 230);
    add(scrollPane, BorderLayout.CENTER);

    userTable = new JTable();
    scrollPane.setViewportView(userTable);
  }
}
