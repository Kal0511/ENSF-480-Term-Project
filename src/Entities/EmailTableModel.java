package src.Entities;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * EmailTableModel is an entity class used to store a list of email data
 * Also acts as a helper class to display the data in a tabular format.
 */
public class EmailTableModel extends AbstractTableModel {

  private static final int SENDER_COL = 0;
  private static final int MESSAGE_COL = 1;
  private String[] columnNames = { "Sender", "Email" };
  private List<Email> emails;

	/**
	 * Constructor.
	 *
	 * @param theemails      list of emails 
	 */
  public EmailTableModel(List<Email> theemails) {
    emails = theemails;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return emails.size();
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    Email tempEmail = emails.get(row);

    switch (col) {
      case SENDER_COL:
        return tempEmail.getSender();
      case MESSAGE_COL:
        return tempEmail.getMessage();
      default:
        return tempEmail.getSender();
    }
  }
  /*
  @Override
  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }
  */
}
