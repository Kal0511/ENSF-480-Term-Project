package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * SearchPropertyTableModel is an entity class used to store a list of property search query data
 * Also acts as a helper class to display the data in a tabular format.
 */
public class SearchPropertyTableModel extends AbstractTableModel {

  private static final int ADDRESS_COL = 0;
  private static final int TYPE_COL = 1;
  private static final int BEDROOM_COL = 2;
  private static final int BATHROOM_COL = 3;
  private static final int FURNISHED_COL = 4;
  private static final int QUADRANT_COL = 5;

  private String[] columnNames = {
    "Address",
    "Type",
    "Bedrooms",
    "Bathrooms",
    "Furnished",
    "Quadrant",
  };
  private List<Property> properties;

	/**
	 * Constructor.
	 *
	 * @param theproperties    list of properties
	 */
  public SearchPropertyTableModel(List<Property> theproperties) {
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
      case TYPE_COL:
        return tempProperty.getType();
      case BEDROOM_COL:
        return tempProperty.getBedrooms();
      case BATHROOM_COL:
        return tempProperty.getBathrooms();
      case FURNISHED_COL:
        return tempProperty.isFurnished();
      case QUADRANT_COL:
        return tempProperty.getQuadrant();
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
