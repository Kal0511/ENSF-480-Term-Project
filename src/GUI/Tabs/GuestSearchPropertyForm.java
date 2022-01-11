package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * GuestSearchPropertyForm builds and displays the search form tab used by a guest user
 * Allows user to search for specific properties by changing a wide range of parameters
 */
public class GuestSearchPropertyForm extends GUI {

  private JLabel typeLabel;
  private JComboBox<String> typeBox;
  private JLabel bedroomsLabel;
  private JComboBox<String> bedroomsBox;
  private JLabel bathroomsLabel;
  private JComboBox<String> bathroomsBox;
  private JLabel furnishedLabel;
  private JCheckBox furnishedBox;
  private JLabel quadrantLabel;
  private JComboBox<String> quadrantBox;
  private JButton searchButton;
  private JScrollPane scrollPane;
  private JTable propertyTable;

  // private JLabel status;

  /**
   * Constructor
   */
  public GuestSearchPropertyForm() {
    super();
    typeLabel = new JLabel("Type:");
    typeLabel.setBounds(40, 20, 80, 25);
    quadrantLabel = new JLabel("Quadrant:");
    quadrantLabel.setBounds(290, 20, 80, 25);
    bedroomsLabel = new JLabel("Bedrooms:");
    bedroomsLabel.setBounds(40, 50, 80, 25);
    bathroomsLabel = new JLabel("Bathrooms:");
    bathroomsLabel.setBounds(290, 50, 80, 25);
    furnishedLabel = new JLabel("Furnished:");
    furnishedLabel.setBounds(40, 80, 80, 25);

    String[] types = {
      "Any",
      "Apartment",
      "Attached House",
      "Detached House",
      "Townhouse",
    };
    typeBox = new JComboBox<String>(types);
    typeBox.setBounds(110, 20, 100, 25);
    String[] quadrant = { "Any", "NE", "NW", "SE", "SW" };
    quadrantBox = new JComboBox<String>(quadrant);
    quadrantBox.setBounds(360, 20, 100, 25);
    String[] bedrooms = { "0", "1", "2", "3", "4", "5", "6", "7" };
    bedroomsBox = new JComboBox<String>(bedrooms);
    bedroomsBox.setBounds(110, 50, 100, 25);
    String[] bathrooms = { "0", "1", "2", "3", "4", "5", "6", "7" };
    bathroomsBox = new JComboBox<String>(bathrooms);
    bathroomsBox.setBounds(360, 50, 100, 25);
    furnishedBox = new JCheckBox();
    furnishedBox.setBounds(110, 80, 100, 25);

    add(typeLabel);
    add(typeBox);
    add(bedroomsLabel);
    add(bedroomsBox);
    add(bathroomsLabel);
    add(bathroomsBox);
    add(furnishedLabel);
    add(furnishedBox);
    add(quadrantLabel);
    add(quadrantBox);

    searchButton = new JButton("Search");
    searchButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String type = typeBox.getSelectedItem().toString().toLowerCase();
          int bedrooms = Integer.parseInt(
            bedroomsBox.getSelectedItem().toString()
          );
          int bathrooms = Integer.parseInt(
            bathroomsBox.getSelectedItem().toString()
          );
          String quadrant = quadrantBox.getSelectedItem().toString();
          Boolean furnished = furnishedBox.isSelected();
          List<Property> properties = null;
          try {
            properties =
              rentalDAO.searchProperties(
                type,
                bedrooms,
                bathrooms,
                quadrant,
                furnished
              );
            SearchPropertyTableModel model = new SearchPropertyTableModel(
              properties
            );
            propertyTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              GuestSearchPropertyForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    searchButton.setBounds(210, 110, 100, 25);
    add(searchButton);

    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 150, 500, 230);
    add(scrollPane, BorderLayout.CENTER);

    propertyTable = new JTable();
    scrollPane.setViewportView(propertyTable);
  }
}
