package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * ViewEmailWindow builds and displays the email viewer tab used view and respond to emails.
 */
public class ViewEmailWindow extends GUI {

  private JButton viewEmailsButton;
  private JScrollPane scrollPane;
  private JTable emailTable;
  private JButton deleteEmailButton;
  private JButton respondButton;

	/**
	 * Constructor
	 * 
	 * @param user			username
	 */
  public ViewEmailWindow(String user) {
    super();
    viewEmailsButton = new JButton("Show Emails");
    viewEmailsButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          deleteEmailButton.setVisible(true);
          respondButton.setVisible(true);
          viewEmailsButton.setText("Refresh Emails");
          List<Email> emails = null;
          try {
            emails = rentalDAO.getUserEmails(user);
            EmailTableModel model = new EmailTableModel(emails);
            emailTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewEmailWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    deleteEmailButton = new JButton("Delete Message");
    deleteEmailButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String sender = String.valueOf(
            emailTable.getValueAt(emailTable.getSelectedRow(), 0)
          );
          String message = String.valueOf(
            emailTable.getValueAt(emailTable.getSelectedRow(), 1)
          );
          try {
            rentalDAO.deleteEmail(sender, user, message);
            JOptionPane.showMessageDialog(
              null,
              "Refresh to show email deleted."
            );
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewEmailWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    deleteEmailButton.setBounds(100, 295, 140, 25);
    deleteEmailButton.setVisible(false);
    add(deleteEmailButton);

    respondButton = new JButton("Reply");
    respondButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String sendback = String.valueOf(
            emailTable.getValueAt(emailTable.getSelectedRow(), 0)
          );
          try {
            String message = JOptionPane.showInputDialog(
              "What would you like to email to '" + sendback + "'?"
            );
            if (message != null) rentalDAO.enterEmail(user, sendback, message);
            JOptionPane.showMessageDialog(null, "Email Sent.");
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewEmailWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    respondButton.setBounds(300, 295, 140, 25);
    respondButton.setVisible(false);
    add(respondButton);
    viewEmailsButton.setBounds(200, 40, 130, 25);
    add(viewEmailsButton);
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 90, 500, 180);
    add(scrollPane, BorderLayout.CENTER);

    emailTable = new JTable();
    scrollPane.setViewportView(emailTable);
  }
}
