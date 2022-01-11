package src.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import src.Entities.*;

/**
 * AccessPropertiesForm builds and displays the property deletion tab used by a manager to delete properties.
 */
public class AccessPropertiesForm extends GUI {

  private JButton viewPropertiesButton;
  private JButton deletePropertyButton;
  private JScrollPane scrollPane;
  private JTable propertyTable;

  /**
   * Constructor
   */
  public AccessPropertiesForm() {
    super();
    viewPropertiesButton = new JButton("Show Properties");
    viewPropertiesButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          viewPropertiesButton.setText("Refresh Properties");
          deletePropertyButton.setVisible(true);
          List<Property> properties = null;
          try {
            properties = rentalDAO.getAllProperties();
            PropertyTableModel model = new PropertyTableModel(properties);
            propertyTable.setModel(model);
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              AccessPropertiesForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    viewPropertiesButton.setBounds(200, 40, 150, 25);
    add(viewPropertiesButton);
    deletePropertyButton = new JButton("Delete Property");
    deletePropertyButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int id = Integer.parseInt(
            String.valueOf(
              propertyTable.getValueAt(propertyTable.getSelectedRow(), 0)
            )
          );

          try {
            rentalDAO.deleteProperty(id);
            JOptionPane.showMessageDialog(
              null,
              "Refresh to show property deleted."
            );
          } catch (Exception exc) {
            JOptionPane.showMessageDialog(
              AccessPropertiesForm.this,
              "Error: " + exc,
              "Error",
              JOptionPane.ERROR_MESSAGE
            );
          }
        }
      }
    );
    deletePropertyButton.setBounds(200, 350, 150, 25);
    deletePropertyButton.setVisible(false);
    add(deletePropertyButton);
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    scrollPane.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scrollPane.setBounds(20, 90, 500, 230);
    add(scrollPane, BorderLayout.CENTER);

    propertyTable = new JTable();
    scrollPane.setViewportView(propertyTable);
  }
}
