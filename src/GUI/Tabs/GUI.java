package src.GUI.Tabs;

import javax.swing.*;
import src.Database.RentalDatabaseObject;

/**
 * GUI is the parent class containing base functionality used by different
 * child. Contains a database connection object which all child class can used
 * to talk to the database server.
 */
public class GUI extends JPanel {

	protected RentalDatabaseObject rentalDAO;

	/**
	 * Constructor
	 */
	GUI() {
		try {
			rentalDAO = new RentalDatabaseObject();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		setLayout(null);
	}
}
