package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * StatePropertyTableModel is an entity class used to store a list of property status data
 * Also acts as a helper class to display the data in a tabular format.
 */
public class StatePropertyTableModel extends AbstractTableModel {

  private static final int ADDRESS_COL = 0;
  private static final int STATUS_COL = 1;
  private String[] columnNames = { "Address", "Status" };
  private List<Property> properties;

	/**
	 * Constructor.
	 *
	 * @param theproperties    list of properties
	 */
  public StatePropertyTableModel(List<Property> theproperties) {
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
      case ADDRESS_COL:
        return tempProperty.getAddress();
      case STATUS_COL:
        return tempProperty.getStatus();
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
