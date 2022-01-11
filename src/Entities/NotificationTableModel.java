package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import src.Database.RentalDatabaseObject;

/**
 * NotificationTableModel is an entity class used to store a list of notification data
 * Also acts as a helper class to display the data in a tabular format.
 */
public class NotificationTableModel extends AbstractTableModel {

  private static final int SUBSCRIPTIONID_COL = 0;
  private static final int ADDRESS_COL = 1;
  private static final int TYPE_COL = 2;
  private static final int BEDROOM_COL = 3;
  private static final int BATHROOM_COL = 4;
  private static final int FURNISHED_COL = 5;
  private static final int QUADRANT_COL = 6;

  private RentalDatabaseObject dao;
  private String[] columnNames = {
    "SubscriptionID",
    "Address",
    "Type",
    "Bedrooms",
    "Bathrooms",
    "Furnished",
    "Quadrant",
  };
  private List<Notification> notifications;

	/**
	 * Constructor.
	 *
	 * @param thenotifications    list of notifications
	 */
  public NotificationTableModel(List<Notification> thenotifications)
    throws Exception {
    notifications = thenotifications;
    dao = new RentalDatabaseObject();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return notifications.size();
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    Notification tempNotifcation = notifications.get(row);

    switch (col) {
      case SUBSCRIPTIONID_COL:
        return tempNotifcation.getSubscriptionID();
      case ADDRESS_COL:
        try {
          return dao.getProperty(tempNotifcation.getListingID()).getAddress();
        } catch (Exception e) {
          e.printStackTrace();
        }
      case TYPE_COL:
        try {
          return dao.getProperty(tempNotifcation.getListingID()).getType();
        } catch (Exception e) {
          e.printStackTrace();
        }
      case BEDROOM_COL:
        try {
          return dao.getProperty(tempNotifcation.getListingID()).getBedrooms();
        } catch (Exception e) {
          e.printStackTrace();
        }
      case BATHROOM_COL:
        try {
          return dao.getProperty(tempNotifcation.getListingID()).getBathrooms();
        } catch (Exception e) {
          e.printStackTrace();
        }
      case FURNISHED_COL:
        try {
          return dao.getProperty(tempNotifcation.getListingID()).isFurnished();
        } catch (Exception e) {
          e.printStackTrace();
        }
      case QUADRANT_COL:
        try {
          return dao.getProperty(tempNotifcation.getListingID()).getQuadrant();
        } catch (Exception e) {
          e.printStackTrace();
        }
      default:
        return tempNotifcation.getSubscriptionID();
    }
  }
  /*
 @Override
 public Class getColumnClass(int c) {
   return getValueAt(0, c).getClass();
  }
 */
}
