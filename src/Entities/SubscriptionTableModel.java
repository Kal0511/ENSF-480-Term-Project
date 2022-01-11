package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * SubscriptionTableModel is an entity class used to store a list of user subscription data.
 * Also acts as a helper class to display the data in a tabular format.
 */
public class SubscriptionTableModel extends AbstractTableModel {

  private static final int ID_COL = 0;
  private static final int TYPE_COL = 1;
  private static final int BEDROOM_COL = 2;
  private static final int BATHROOM_COL = 3;
  private static final int FURNISHED_COL = 4;
  private static final int QUADRANT_COL = 5;

  private String[] columnNames = {
    "ID",
    "Type",
    "Bedrooms",
    "Bathrooms",
    "Furnished",
    "Quadrant",
  };
  private List<Subscription> subscriptions;

	/**
	 * Constructor.
	 *
	 * @param thesubscriptions    list of subscriptions
	 */
  public SubscriptionTableModel(List<Subscription> thesubscriptions) {
    subscriptions = thesubscriptions;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return subscriptions.size();
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    Subscription tempSubscription = subscriptions.get(row);

    switch (col) {
      case ID_COL:
        return tempSubscription.getId();
      case TYPE_COL:
        return tempSubscription.getType();
      case BEDROOM_COL:
        return tempSubscription.getBedrooms();
      case BATHROOM_COL:
        return tempSubscription.getBathrooms();
      case FURNISHED_COL:
        return tempSubscription.isFurnished();
      case QUADRANT_COL:
        return tempSubscription.getQuadrant();
      default:
        return tempSubscription.getId();
    }
  }
  /*
 @Override
 public Class getColumnClass(int c) {
   return getValueAt(0, c).getClass();
  }
 */
}
