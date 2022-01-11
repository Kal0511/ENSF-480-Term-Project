package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * UserTableModel is an entity class used to store a list of user data
 * Also acts as a helper class to display the data in a tabular format.
 */
public class UserTableModel extends AbstractTableModel {

  private static final int USERNAME_COL = 0;
  private static final int PASSWORD_COL = 1;
  private static final int ACCOUNTTYPE_COL = 2;

  private String[] columnNames = { "Username", "Password", "AccountType" };
  private List<User> users;

	/**
	 * Constructor.
	 *
	 * @param theusers    list of users
	 */
  public UserTableModel(List<User> theusers) {
    users = theusers;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return users.size();
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    User tempUser = users.get(row);

    switch (col) {
      case USERNAME_COL:
        return tempUser.getUsername();
      case PASSWORD_COL:
        return tempUser.getPassword();
      case ACCOUNTTYPE_COL:
        return tempUser.getAccountType();
      default:
        return tempUser.getUsername();
    }
  }
  /*
 @Override
 public Class getColumnClass(int c) {
   return getValueAt(0, c).getClass();
  }
 */
}
