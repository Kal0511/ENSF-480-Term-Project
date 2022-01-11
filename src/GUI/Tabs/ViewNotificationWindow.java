package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * ViewNotificationWindow builds and displays the notifications viewer tab used to view notifications from properties.
 */
public class ViewNotificationWindow extends GUI {

  private JButton viewNotificationsButton;
  private JButton deleteNotificationsButton;
  private JScrollPane scrollPane;
  private JTable notificationTable;

	/**
	 * Constructor
	 * 
	 * @param user			username
	 */
  public ViewNotificationWindow(String user) {
    super();
    viewNotificationsButton = new JButton("Show Notifications");
    viewNotificationsButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          viewNotificationsButton.setText("Refresh Notifications");
          deleteNotificationsButton.setVisible(true);
          List<Notification> notifications = null;
          try {
            notifications = rentalDAO.getRentersNotifications(user);
            NotificationTableModel model = new NotificationTableModel(
              notifications
            );
            notificationTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewNotificationWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    viewNotificationsButton.setBounds(200, 40, 150, 25);
    add(viewNotificationsButton);
    deleteNotificationsButton = new JButton("Delete Notification");
    deleteNotificationsButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int subscriptionid = Integer.parseInt(
            String.valueOf(
              notificationTable.getValueAt(
                notificationTable.getSelectedRow(),
                0
              )
            )
          );
          String address = String.valueOf(
            notificationTable.getValueAt(notificationTable.getSelectedRow(), 1)
          );
          try {
            rentalDAO.deleteNotification(subscriptionid, address, user);
            JOptionPane.showMessageDialog(
              null,
              "Refresh to show notifications deleted."
            );
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewNotificationWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    deleteNotificationsButton.setBounds(200, 350, 150, 25);
    deleteNotificationsButton.setVisible(false);
    add(deleteNotificationsButton);
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 90, 500, 230);
    add(scrollPane, BorderLayout.CENTER);

    notificationTable = new JTable();
    scrollPane.setViewportView(notificationTable);
  }
}
