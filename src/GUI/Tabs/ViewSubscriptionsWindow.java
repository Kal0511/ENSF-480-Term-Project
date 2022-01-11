package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * ViewSubscriptionsWindow builds and displays the subscription viewer tab used to view subscriptions.
 */
public class ViewSubscriptionsWindow extends GUI {

  private JButton viewSubscriptionsButton;
  private JButton deleteSubscriptionsButton;
  private JScrollPane scrollPane;
  private JTable subscriptionTable;

	/**
	 * Constructor
	 */
  public ViewSubscriptionsWindow() {
    super();
    viewSubscriptionsButton = new JButton("Show Subscriptions");
    viewSubscriptionsButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          viewSubscriptionsButton.setText("Refresh Subscriptions");
          deleteSubscriptionsButton.setVisible(true);
          List<Subscription> subscriptions = null;
          try {
            subscriptions = rentalDAO.getAllSubscriptions();
            SubscriptionTableModel model = new SubscriptionTableModel(
              subscriptions
            );
            subscriptionTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewSubscriptionsWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    viewSubscriptionsButton.setBounds(200, 40, 150, 25);
    add(viewSubscriptionsButton);
    deleteSubscriptionsButton = new JButton("Delete Subscription");
    deleteSubscriptionsButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int id = Integer.parseInt(
            String.valueOf(
              subscriptionTable.getValueAt(
                subscriptionTable.getSelectedRow(),
                0
              )
            )
          );
          try {
            rentalDAO.deleteSubscription(id);
            JOptionPane.showMessageDialog(
              null,
              "Refresh to show subscriptions deleted."
            );
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ViewSubscriptionsWindow.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    deleteSubscriptionsButton.setBounds(200, 350, 150, 25);
    deleteSubscriptionsButton.setVisible(false);
    add(deleteSubscriptionsButton);
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 90, 500, 230);
    add(scrollPane, BorderLayout.CENTER);

    subscriptionTable = new JTable();
    scrollPane.setViewportView(subscriptionTable);
  }
}
