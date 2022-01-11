package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * ManageStateListingForm builds and displays the property state tab used by a landlord to change the state of properties.
 */
public class ManageStateListingForm extends GUI {

  private JButton viewPropertiesButton;
  private JButton changeStateButton;
  private JScrollPane scrollPane;
  private JTable propertyTable;

	/**
	 * Constructor
	 * 
	 * @param user			username
	 * @param accountType	type of user
	 */
  public ManageStateListingForm(String user, String accountType) {
    super();
    viewPropertiesButton = new JButton("Show Properties");
    viewPropertiesButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          viewPropertiesButton.setText("Refresh Statuses");
          changeStateButton.setVisible(true);
          List<Property> properties = null;
          try {
            if (accountType == "landlord") properties =
              rentalDAO.getLandlordProperties(user);
            if (accountType == "manager") properties =
              rentalDAO.getAllProperties();
            StatePropertyTableModel model = new StatePropertyTableModel(
              properties
            );
            propertyTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ManageStateListingForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    changeStateButton = new JButton("Change State");
    changeStateButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String address = String.valueOf(
            propertyTable.getValueAt(propertyTable.getSelectedRow(), 0)
          );
          try {
            String[] statusOptions = {
              "active",
              "suspended",
              "cancelled",
              "occupied",
            };
            String status = (String) JOptionPane.showInputDialog(
              null,
              "Change " + address + " state to:",
              "Update Listing state",
              JOptionPane.QUESTION_MESSAGE,
              null,
              statusOptions,
              statusOptions[0]
            );
            if (status != null) {
              if (
                String
                  .valueOf(
                    propertyTable.getValueAt(propertyTable.getSelectedRow(), 1)
                  )
                  .equals("payment required") &&
                status.equals("active")
              ) {
                JOptionPane.showMessageDialog(
                  null,
                  "The listing cannot be 'active' since it requires payment first."
                );
              } else {
                rentalDAO.changePropertyStatus(address, status);
                JOptionPane.showMessageDialog(
                  null,
                  "Status Updated!Click 'Refresh Statuses' to refresh the statuses."
                );
              }
            }
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ManageStateListingForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    changeStateButton.setBounds(200, 295, 140, 25);
    changeStateButton.setVisible(false);
    add(changeStateButton);
    viewPropertiesButton.setBounds(200, 40, 140, 25);
    add(viewPropertiesButton);

    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 90, 500, 180);
    add(scrollPane, BorderLayout.CENTER);

    propertyTable = new JTable();
    scrollPane.setViewportView(propertyTable);
  }
}
