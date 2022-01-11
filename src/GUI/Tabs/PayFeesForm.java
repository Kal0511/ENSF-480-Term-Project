package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * PayFeesForm builds and displays the pay fee tab used by a landlord to pay to extend the expiration date of owned properties.
 */
public class PayFeesForm extends GUI {

  private JButton viewPropertiesButton;
  private JScrollPane scrollPane;
  private JTable propertyTable;
  private JButton extendDateButton;
  private JLabel feePriceLabel;
  private JLabel feePeriodLabel;
  private int feePrice;
  private int feePeriod;

	/**
	 * Constructor
	 * 
	 * @param user			username
	 * @param accountType	type of user
	 */
  public PayFeesForm(String user, String accountType) {
    super();
    feePriceLabel = new JLabel("");
    feePriceLabel.setBounds(100, 340, 200, 25);
    add(feePriceLabel);

    feePeriodLabel = new JLabel("");
    feePeriodLabel.setBounds(100, 370, 200, 25);
    add(feePeriodLabel);

    viewPropertiesButton = new JButton("Show Properties");
    viewPropertiesButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          viewPropertiesButton.setText("Refresh Statuses");
          extendDateButton.setVisible(true);
          List<Property> properties = null;
          try {
            feePrice = rentalDAO.getFeePrice();
            feePeriod = rentalDAO.getFeePeriod();
            feePriceLabel.setText("Current Fee Per Period:    $" + feePrice);
            feePeriodLabel.setText(
              "Current Period Duration:   " + feePeriod + " days"
            );
            if (accountType == "landlord") properties =
              rentalDAO.getLandlordProperties(user);
            if (accountType == "manager") properties =
              rentalDAO.getAllProperties();
            ExpiryPropertyTable model = new ExpiryPropertyTable(properties);
            propertyTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              PayFeesForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    extendDateButton = new JButton("Pay Extension");
    extendDateButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String address = String.valueOf(
            propertyTable.getValueAt(propertyTable.getSelectedRow(), 0)
          );
          String expiry = String.valueOf(
            propertyTable.getValueAt(propertyTable.getSelectedRow(), 1)
          );
          try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date tempDate = formatter.parse(expiry);
            LocalDate newDate = new java.sql.Date(tempDate.getTime())
              .toLocalDate();

            String[] periodOptions = {
              "1 period (" +
              feePeriod +
              " days for $" +
              feePrice +
              ") until " +
              String.valueOf(newDate.plusDays(feePeriod)),
              "2 periods (" +
              2 *
              feePeriod +
              " days for $" +
              2 *
              feePrice +
              ") until " +
              String.valueOf(newDate.plusDays(2 * feePeriod)),
              "3 periods (" +
              3 *
              feePeriod +
              " days for $" +
              3 *
              feePrice +
              ") until " +
              String.valueOf(newDate.plusDays(3 * feePeriod)),
            };
            String period = (String) JOptionPane.showInputDialog(
              null,
              "How many more periods would you like to extend " +
              address +
              " to?",
              "Extend Listing Period",
              JOptionPane.QUESTION_MESSAGE,
              null,
              periodOptions,
              periodOptions[0]
            );
            if (period != null) {
              int daysExtended =
                Character.getNumericValue(period.charAt(0)) *
                rentalDAO.getFeePeriod();
              rentalDAO.changePropertyExpiry(address, expiry, daysExtended);
              JOptionPane.showMessageDialog(
                null,
                "Status Updated! Expired listings will not be changed to active until paid. Click 'Refresh Statuses' to refresh the statuses."
              );
            }
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              PayFeesForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    extendDateButton.setBounds(200, 295, 140, 25);
    extendDateButton.setVisible(false);
    add(extendDateButton);
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
