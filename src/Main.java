/**
 * ENSF 480: Term Project - Rental.jar
 * Group 15: Amanda Nguyen, Liam, Vrund Patel, Kaumil Patel
 *
 * @author Amanda <a href="mailto:amanda.nguyen1@ucalgary.ca"> amanda.nguyen1@ucalgary.ca</a>
 * @author
 * @author
 * @author
 * @version 1.6
 * @since 1.0
 */

/*
<!--INSTRUCTIONS-->


(1)Run the program using the following line in the command prompt:
"java -jar ENSF480TermProjectGroup15.jar"
*/

package src;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.*;
import src.Database.RentalDatabaseObject;
import src.GUI.Menu.*;
import src.GUI.Menu.Menu;

/**
 * The Main class house the main method that is use to run the program
 */
public class Main extends JFrame {

  /**
   * The main method is use to run the program First, it makes a connection to the
   * database using the promted user input. Then it tries to find the lowest
   * priced combination for the requested item (Which is also taken in through a
   * promted input). An output is produced detailed the success/fail of the order
   * and process can be repeated.
   *
   * @param args
   */
  /**
   * Launch the application.
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    EventQueue.invokeLater(
      new Runnable() {
        public void run() {
          try {
            getDatabaseAccount();
            Boolean testing = false;
            if (!testing) {
              Menu frame = new GuestMenu();
              frame.setVisible(true);
            } else {
              Menu frame1 = new LandlordMenu("moussavifan");
              frame1.setVisible(true);
              Menu frame2 = new RenterMenu("renter1");
              frame2.setVisible(true);
              Menu frame3 = new ManagerMenu("manager");
              frame3.setVisible(true);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    );
  }

  public static void getDatabaseAccount() throws Exception {
    File myObj = new File("account.properties");
    boolean filemade = myObj.createNewFile();
    boolean alreadytried = false;
    do {
      try {
        RentalDatabaseObject temp = new RentalDatabaseObject();
        break;
      } catch (SQLException e) {
        if (alreadytried) {
          if (
            JOptionPane.showConfirmDialog(
              null,
              "Database information was invalid. Try Again?",
              "Error",
              JOptionPane.YES_OPTION
            ) ==
            JOptionPane.NO_OPTION
          ) {
            System.exit(0);
          }
        } else if (!filemade) {
          if (
            JOptionPane.showConfirmDialog(
              null,
              "Current Database Information is invalid. Enter New Information?",
              "Error",
              JOptionPane.YES_OPTION
            ) ==
            JOptionPane.NO_OPTION
          ) {
            System.exit(0);
          }
        }

        OutputStream output = new FileOutputStream("account.properties");
        output.flush();
        Properties prop = new Properties();
        String username = JOptionPane.showInputDialog(
          "Please enter your MySQL connection username"
        );
        String password = JOptionPane.showInputDialog(
          "Please enter your MySQL connection password"
        );
        String host = JOptionPane.showInputDialog(
          "What is the host of your MySQL connection?"
        );
        String port = JOptionPane.showInputDialog(
          "What is the port of your MySQL connection?"
        );
        try {
          prop.setProperty("user", username);
          prop.setProperty("password", password);
          prop.store(output, null);
          String temp = "dburl=jdbc:mysql://" + host + ":" + port + "/ensf480";
          output.write(temp.getBytes(Charset.forName("UTF-8")));
          alreadytried = true;
        } catch (IOException i) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        // set the properties value

        //try to initialize database
      }
    } while (true);
  }
}
