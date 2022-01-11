package src.GUI.Tabs;

import java.awt.event.*;
import javax.swing.*;

/**
 * ManageFeesForm builds and displays the fee form tab used by a manager to alter the fee period and amount.
 */
public class ManageFeesForm extends GUI {

  private JButton viewFeesButton;

  private JLabel feePriceLabel;
  private JLabel feePeriodLabel;
  private int feePrice;
  private int feePeriod;

  private JLabel priceLabel;
  private JTextField priceText;
  private JLabel periodLabel;
  private JTextField periodText;
  private JButton updateInfoButton;

	/**
	 * Constructor
	 */
  public ManageFeesForm() {
    super();
    feePriceLabel = new JLabel("");
    feePriceLabel.setBounds(140, 70, 200, 25);
    add(feePriceLabel);

    feePeriodLabel = new JLabel("");
    feePeriodLabel.setBounds(140, 100, 200, 25);
    add(feePeriodLabel);

    viewFeesButton = new JButton("View Fee Info");
    viewFeesButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          viewFeesButton.setText("Refresh Fee Info");
          periodLabel.setVisible(true);
          periodText.setVisible(true);
          priceText.setVisible(true);
          priceLabel.setVisible(true);
          updateInfoButton.setVisible(true);
          try {
            feePrice = rentalDAO.getFeePrice();
            feePeriod = rentalDAO.getFeePeriod();
            feePriceLabel.setText("Current Fee Per Period:    $" + feePrice);
            feePeriodLabel.setText(
              "Current Period Duration:   " + feePeriod + " days"
            );
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ManageFeesForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );

    viewFeesButton.setBounds(190, 30, 150, 25);
    add(viewFeesButton);

    priceLabel = new JLabel("Price ($)");
    priceLabel.setBounds(140, 190, 80, 25);
    priceLabel.setVisible(false);
    add(priceLabel);

    priceText = new JTextField(20);
    priceText.setBounds(230, 190, 165, 25);
    priceText.setVisible(false);
    add(priceText);

    periodLabel = new JLabel("Period (days)");
    periodLabel.setBounds(140, 230, 80, 25);
    periodLabel.setVisible(false);
    add(periodLabel);

    periodText = new JTextField(20);
    periodText.setBounds(230, 230, 165, 25);
    periodText.setVisible(false);
    add(periodText);

    updateInfoButton = new JButton("Update Fee Info");
    updateInfoButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            String newPriceText = priceText.getText();
            String newPeriodText = periodText.getText();
            if (
              newPriceText.length() > 0 &&
              newPriceText.matches("^[0-9]*$") &&
              newPeriodText.length() > 0 &&
              newPeriodText.matches("^[0-9]*$")
            ) {
              feePrice = Integer.parseInt(newPriceText);
              feePeriod = Integer.parseInt(newPeriodText);
              rentalDAO.setFeeInfo(feePrice, feePeriod);
              JOptionPane.showMessageDialog(
                null,
                "Fee successfully updated. Refresh to see changes."
              );
            } else {
              JOptionPane.showMessageDialog(
                null,
                "Fields must be unsigned integers only."
              );
            }
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              ManageFeesForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    updateInfoButton.setVisible(false);
    updateInfoButton.setBounds(190, 295, 150, 25);
    add(updateInfoButton);
  }
}
