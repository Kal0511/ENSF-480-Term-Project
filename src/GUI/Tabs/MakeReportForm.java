package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * MakeReportForm builds and displays the report form tab used by a manager
 */
public class MakeReportForm extends GUI {

  private JButton makeReportButton;
  private JLabel periodListingsLabel;
  private JLabel periodRentalsLabel;
  private JLabel activeListingsLabel;
  private int periodListings;
  private int periodRentals;
  private int activeListings;
  private JScrollPane scrollPane;
  private JLabel periodRentalsTableLabel;
  private JTable periodRentalsTable;

	/**
	 * Constructor
	 */
  public MakeReportForm() {
    super();
    makeReportButton = new JButton("Show Report");
    makeReportButton.setBounds(200, 30, 130, 25);

    periodListingsLabel = new JLabel("");
    periodListingsLabel.setBounds(140, 70, 200, 25);
    add(periodListingsLabel);

    periodRentalsLabel = new JLabel("");
    periodRentalsLabel.setBounds(140, 100, 200, 25);
    add(periodRentalsLabel);

    activeListingsLabel = new JLabel("");
    activeListingsLabel.setBounds(140, 130, 200, 25);
    add(activeListingsLabel);

    periodRentalsTableLabel =
      new JLabel("List of Houses Rented in the Period:");
    periodRentalsTableLabel.setBounds(20, 185, 300, 25);
    periodRentalsTableLabel.setFont(new Font("Arial", Font.ITALIC, 16));
    periodRentalsTableLabel.setVisible(false);
    add(periodRentalsTableLabel);

    makeReportButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          makeReportButton.setText("Refresh Report");
          List<Property> periodRentalsList = null;
          try {
            periodListings =
              rentalDAO
                .getPeriodLogs("active", rentalDAO.getFeePeriod())
                .size();
            periodRentals =
              rentalDAO
                .getPeriodLogs("occupied", rentalDAO.getFeePeriod())
                .size();
            activeListings = rentalDAO.getProperties("active").size();
            periodRentalsTableLabel.setVisible(true);
            periodListingsLabel.setText(
              "Listings this Period:             " + periodListings
            );
            periodRentalsLabel.setText(
              "Rentals this Period:              " + periodRentals
            );
            activeListingsLabel.setText(
              "Current Active Listings:       " + activeListings
            );
            periodRentalsList =
              rentalDAO.getPeriodLogs("occupied", rentalDAO.getFeePeriod());
            RentedPropertiesTableModel model = new RentedPropertiesTableModel(
              periodRentalsList
            );
            periodRentalsTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              MakeReportForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    add(makeReportButton);
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 220, 500, 200);
    add(scrollPane, BorderLayout.CENTER);

    periodRentalsTable = new JTable();
    scrollPane.setViewportView(periodRentalsTable);
  }
}
