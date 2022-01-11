package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * RentedPropertiesTableModel is an entity class used to store a list of rented property data
 * Also acts as a helper class to display the data in a tabular format.
 */
public class RentedPropertiesTableModel extends AbstractTableModel {

  private static final int OWNER_COL = 0;
  private static final int ID_COL = 1;
  private static final int ADDRESS_COL = 2;

  private String[] columnNames = { "Landlord", "House ID", "Address" };
  private List<Property> properties;

	/**
	 * Constructor.
	 *
	 * @param theproperties    list of rented properties
	 */
  public RentedPropertiesTableModel(List<Property> theproperties) {
    properties = theproperties;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return properties.size();
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    Property tempProperty = properties.get(row);

    switch (col) {
      case OWNER_COL:
        return tempProperty.getOwner();
      case ID_COL:
        return tempProperty.getId();
      case ADDRESS_COL:
        return tempProperty.getAddress();
      default:
        return tempProperty.getAddress();
    }
  }
  /*
 @Override
 public Class getColumnClass(int c) {
   return getValueAt(0, c).getClass();
  }
 */
}
