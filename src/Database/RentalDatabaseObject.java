package src.Database;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import src.Entities.*;

/**
 * RentalDatabaseObject contains functionality for connecting and exchanging data with the database
 */
public class RentalDatabaseObject {

  private Connection myConn;
  private static boolean logsInitialized = false;

  /**
   * Constructor
   *
   * @throws SQLException For handling exceptions regarding MySQL
   */
  public RentalDatabaseObject() throws Exception {
    // get db properties
    Properties acct = new Properties();
    acct.load(new FileInputStream("account.properties"));

    String user = acct.getProperty("user");
    String password = acct.getProperty("password");
    String dburl = acct.getProperty("dburl");

    // connect to database
    myConn = DriverManager.getConnection(dburl, user, password);

    System.out.println("DB connection successful to: " + dburl);
    if (!logsInitialized) {
      initializeLogs();
      logsInitialized = true;
    }
  }

  /**
   * gets all users in the database
   *
   * @throws SQLException For handling exceptions regarding MySQL
   * @return ArrayList of User objects
   */
  public List<User> getAllUsers() throws Exception {
    List<User> list = new ArrayList<>();
    Statement query = null;
    ResultSet results = null;

    // query formulated
    try {
      query = myConn.createStatement();
      // query initiated
      results = query.executeQuery("SELECT * FROM users");

      // data parsed into list
      while (results.next()) {
        User tempUser = convertRowToUser(results);
        list.add(tempUser);
      }
      return list;
    } finally {
      // safely closing statement
      close(query, results);
    }
  }

  /**
   * gets all users in the database with a specific username
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	username	username of user
   * @return 	ArrayList of User objects
   */
  public List<User> searchUsername(String username) throws Exception {
    List<User> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query = myConn.prepareStatement("SELECT * FROM users WHERE username = ?");
      query.setString(1, username);

      results = query.executeQuery();
      while (results.next()) {
        User tempUser = convertRowToUser(results);
        list.add(tempUser);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * register a new user into the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	newUser		user data
   */
  public void enterUser(User newUser) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "INSERT INTO users (username, password, accounttype) VALUES (?, ?, ?)"
        );
      query.setString(1, newUser.getUsername());
      query.setString(2, newUser.getPassword());
      query.setString(3, newUser.getAccountType());
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * converts user data received from database to User object
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	set		data received from database
   * @return 	User object with user data
   */
  private User convertRowToUser(ResultSet set) throws SQLException {
    String username = set.getString("username");
    String password = set.getString("password");
    String accountType = set.getString("accounttype");

    User tempUser = new User(username, password, accountType);

    return tempUser;
  }

  /**
   * gets all properties in the database
   *
   * @throws SQLException For handling exceptions regarding MySQL
   * @return ArrayList of Property objects
   */
  public List<Property> getAllProperties() throws Exception {
    updateAllPropertyStatus();

    List<Property> list = new ArrayList<>();

    Statement query = null;
    ResultSet results = null;

    try {
      query = myConn.createStatement();
      results = query.executeQuery("SELECT * FROM properties");

      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        list.add(tempProperty);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets properties with a specific id from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	id	ID of requested property
   * @return 	property data of property with specific id
   */
  public Property getProperty(int id) throws Exception {
    Property property = null;
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query = myConn.prepareStatement("SELECT * FROM properties WHERE id = ?");
      query.setInt(1, id);

      results = query.executeQuery();
      while (results.next()) {
        property = convertRowToProperty(results);
      }
      return property;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all properties with a specific address from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	address		address of requested property
   * @return 	property data of property with specific address
   */
  public Property getProperty(String address) throws Exception {
    Property property = null;
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM properties WHERE address = ?");
      query.setString(1, address);

      results = query.executeQuery();
      while (results.next()) {
        property = convertRowToProperty(results);
      }
      return property;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets the owner's username of a specific property from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	address		address of property
   * @return 	property owner's username
   */
  public String getPropertyLandlord(String address) throws Exception {
    String username = null;
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "SELECT owner FROM properties WHERE address = ?"
        );
      query.setString(1, address);

      results = query.executeQuery();
      while (results.next()) {
        username = results.getString("owner");
      }
      return username;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets the property data of a specific property from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	address		address of property
   * @return 	property data
   */
  public List<Property> searchPropertyAddress(String address) throws Exception {
    List<Property> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM properties WHERE address = ?");
      query.setString(1, address);

      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        list.add(tempProperty);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * deletes a specific property stored in the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	address		property's address
   */
  public void deleteProperty(String address) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("DELETE FROM properties WHERE address = ?");
      query.setString(1, address);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * deletes a specific property stored in the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	id		property's id
   */
  public void deleteProperty(int id) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query = myConn.prepareStatement("DELETE FROM properties WHERE id = ?");
      query.setInt(1, id);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * deletes a specific user stored in the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	username		user's username
   */
  public void deleteUser(String username) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query = myConn.prepareStatement("DELETE FROM users WHERE username = ?");
      query.setString(1, username);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * stores email being sent to a specific user in the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	sender		sender's username
   * @param 	receiver	receiver's username
   * @param 	message		email data
   */
  public void enterEmail(String sender, String receiver, String message)
    throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "INSERT INTO emails (sender, receiver, message) VALUES (?, ?, ?)"
        );
      query.setString(1, sender);
      query.setString(2, receiver);
      query.setString(3, message);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * deletes a specific email stored in the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	sender		sender's username
   * @param 	receiver	receiver's username
   * @param 	message		email data
   */
  public void deleteEmail(String sender, String receiver, String message)
    throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "DELETE FROM emails WHERE sender = ? AND receiver = ? AND message = ?"
        );
      query.setString(1, sender);
      query.setString(2, receiver);
      query.setString(3, message);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all the emails sent to a specific user from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	user	receiver's username
   * @return 	list of emails received
   */
  public List<Email> getUserEmails(String user) throws Exception {
    List<Email> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM emails WHERE receiver = ?");
      query.setString(1, user);

      results = query.executeQuery();
      while (results.next()) {
        String sender = results.getString("sender");
        String receiver = results.getString("receiver");
        String message = results.getString("message");
        list.add(new Email(sender, receiver, message));
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all the properties owned by a specific landlord from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	landlord		landlord's username
   * @return 	property data
   */
  public List<Property> getLandlordProperties(String landlord)
    throws Exception {
    updateAllPropertyStatus();

    List<Property> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM properties WHERE owner = ?");
      query.setString(1, landlord);

      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        list.add(tempProperty);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all the properties matching the requirements from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	type			type of property
   * @param 	bedrooms		number of bedrooms
   * @param 	bathrooms		number of bathrooms
   * @param 	quadrant		which quadrant the property is located in
   * @param 	furnished		if the property should be furnished
   * @return 	property data
   */
  public List<Property> searchProperties(
    String type,
    int bedrooms,
    int bathrooms,
    String quadrant,
    Boolean furnished
  )
    throws Exception {
    updateAllPropertyStatus();
    List<Property> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;
    int furnishedInt = 0;
    if (furnished) furnishedInt = 1;
    try {
      String tempquery = "SELECT * FROM properties WHERE";
      if (!type.equals("any")) {
        tempquery += " type = '" + type + "'";
      }
      if (!tempquery.equals("SELECT * FROM properties WHERE")) {
        tempquery += " AND";
      }
      tempquery += " bedrooms >= " + bedrooms;
      tempquery += " AND bathrooms >= " + bathrooms;
      if (!quadrant.equals("Any")) {
        tempquery += " AND quadrant = '" + quadrant + "'";
      }
      tempquery += " AND furnished >= " + furnishedInt;
      tempquery += " AND status = 'active'";
      System.out.println(tempquery);
      query = myConn.prepareStatement(tempquery);

      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        list.add(tempProperty);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * changes the expiration date of a specific property
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	address				address of property
   * @param 	orignalExpiry		original expiration  date
   * @param 	extendedDays		number of days the expiration date is extended
   */
  public void changePropertyExpiry(
    String address,
    String orignalExpiry,
    int extendedDays
  )
    throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "UPDATE properties SET expirydate = ? WHERE address = ?"
        );
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date tempDate = formatter.parse(orignalExpiry);
      LocalDate newDate = new java.sql.Date(tempDate.getTime()).toLocalDate();
      String newExpiry = String.valueOf(newDate.plusDays(extendedDays));

      query.setString(1, newExpiry);
      query.setString(2, address);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
    updateAllPropertyStatus();
  }

  /**
   * changes the status of a specific property
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	address		address of property
   * @param 	status		new status of the property
   */
  public void changePropertyStatus(String address, String status)
    throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;
    String originalStatus = getProperty(address).getStatus();

    if (!status.equals("active") && originalStatus.equals("active")) {
      pruneNotifications(getProperty(address));
    }
    try {
      query =
        myConn.prepareStatement(
          "UPDATE properties SET status = ? WHERE address = ?"
        );
      query.setString(1, status);
      query.setString(2, address);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
      addLog(getProperty(address));
    } finally {
      close(query, results);
    }
    if (status.equals("active") && !originalStatus.equals("active")) {
      notifySubscription(getProperty(address));
    }
    updateAllPropertyStatus();
  }

  private void updateAllPropertyStatus() throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;
    try {
      query =
        myConn.prepareStatement(
          "SELECT * FROM properties WHERE status = 'active' AND expirydate <= ?"
        );
      query.setString(1, java.time.LocalDate.now().toString());
      System.out.println(query.toString());
      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        notifySubscription(tempProperty);
        addLog(tempProperty, "payment required");
      }
    } finally {
      close(query, results);
    }
    query = null;
    results = null;
    try {
      query =
        myConn.prepareStatement(
          "UPDATE properties SET status = 'payment required' WHERE status = 'active' AND expirydate <= ?"
        );
      query.setString(1, java.time.LocalDate.now().toString());
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
    query = null;
    results = null;
    try {
      query =
        myConn.prepareStatement(
          "SELECT * FROM properties WHERE status = 'payment required' AND expirydate > ?"
        );
      query.setString(1, java.time.LocalDate.now().toString());
      System.out.println(query.toString());
      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        notifySubscription(tempProperty);
        addLog(tempProperty, "active");
      }
    } finally {
      close(query, results);
    }
    query = null;
    results = null;
    try {
      query =
        myConn.prepareStatement(
          "UPDATE properties SET status = 'active' WHERE status = 'payment required' AND expirydate > ?"
        );
      query.setString(1, java.time.LocalDate.now().toString());
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * converts property data received from the database into a property object
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	set			raw property data
   * @return 	property data stored in Property object
   */
  private Property convertRowToProperty(ResultSet set) throws SQLException {
    int id = set.getInt("id");
    String address = set.getString("address");
    String owner = set.getString("owner");
    String type = set.getString("type");
    int bedrooms = set.getInt("bedrooms");
    int bathrooms = set.getInt("bathrooms");
    int furnished = set.getInt("furnished");
    String quadrant = set.getString("quadrant");
    String status = set.getString("status");
    String expirydate = set.getString("expirydate");

    boolean furnishedBool = false;
    if (Integer.valueOf(furnished) == 1) {
      furnishedBool = true;
    }

    Property tempProperty = new Property(
      id,
      address,
      owner,
      type,
      bedrooms,
      bathrooms,
      furnishedBool,
      quadrant,
      status,
      expirydate
    );

    return tempProperty;
  }

  /**
   * subscribes a renter to specific property requirements
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	renter			renter's username
   * @param 	type			type of property
   * @param 	bedrooms		number of bedrooms
   * @param 	bathrooms		number of bathrooms
   * @param 	quadrant		which quadrant the property is located in
   * @param 	furnished		if the property should be furnished'
   * @return  int
   */
  public int addSubscription(
    String renter,
    String type,
    int bedrooms,
    int bathrooms,
    String quadrant,
    Boolean furnished
  )
    throws Exception {
    PreparedStatement query = null;
    PreparedStatement checkedquery = null;
    ResultSet results = null;

    try {
      checkedquery =
        myConn.prepareStatement(
          "SELECT * FROM subscriptions WHERE renter = ? AND type = ? AND bedrooms = ? AND bathrooms = ? AND furnished = ? AND quadrant = ?"
        );
      checkedquery.setString(1, renter);
      checkedquery.setString(2, type);
      checkedquery.setInt(3, bedrooms);
      checkedquery.setInt(4, bathrooms);
      int furnishedint = 0;
      if (furnished) furnishedint = 1;

      checkedquery.setInt(5, furnishedint);
      checkedquery.setString(6, quadrant);

      query =
        myConn.prepareStatement(
          "INSERT INTO subscriptions (renter, type, bedrooms, bathrooms, furnished, quadrant) VALUES (?, ?, ?, ?, ?, ?)"
        );
      query.setString(1, renter);
      query.setString(2, type);
      query.setInt(3, bedrooms);
      query.setInt(4, bathrooms);
      query.setInt(5, furnishedint);
      query.setString(6, quadrant);
      System.out.println(query.toString());
      results = checkedquery.executeQuery();
      if (!results.next()) {
        int rowcount = query.executeUpdate();
        System.out.println("Success - " + rowcount + " rows affected.");
        updateAllPropertyStatus();
        return 1;
      }
      return 0;
    } finally {
      close(query, results);
    }
  }

  /**
   * register new property to the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	newProperty		property data
   */
  public void enterProperty(Property newProperty) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "INSERT INTO properties (address, owner, type, bedrooms, bathrooms, furnished, quadrant, status, expirydate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
      query.setString(1, newProperty.getAddress());
      query.setString(2, newProperty.getOwner());
      query.setString(3, newProperty.getType());
      query.setInt(4, newProperty.getBedrooms());
      query.setInt(5, newProperty.getBathrooms());

      int furnishedint = 0;
      if (newProperty.isFurnished()) furnishedint = 1;
      query.setInt(6, furnishedint);

      query.setString(7, newProperty.getQuadrant());
      query.setString(8, newProperty.getStatus());
      query.setString(9, newProperty.getExpirydate());
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
      updateAllPropertyStatus();
    } finally {
      close(query, results);
    }
  }

  /**
   * gets the fee period from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @return 	period of payment
   */
  public int getFeePeriod() throws Exception {
    int days = 0;

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query = myConn.prepareStatement("SELECT perioddays FROM feeinfo");
      results = query.executeQuery();
      while (results.next()) {
        days = results.getInt("perioddays");
      }
      return days;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets the fee amount from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @return 	dollar amount of payment
   */
  public int getFeePrice() throws Exception {
    int price = 0;

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query = myConn.prepareStatement("SELECT price FROM feeinfo");
      results = query.executeQuery();
      while (results.next()) {
        price = results.getInt("price");
      }
      return price;
    } finally {
      close(query, results);
    }
  }

  /**
   * sets new fee period and amount
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	newPrice		dollar amount of payment
   * @param 	newPeriod		period of payment
   */
  public void setFeeInfo(int newPrice, int newPeriod) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "REPLACE INTO feeinfo (price, perioddays) VALUES (" +
          newPrice +
          "," +
          newPeriod +
          ")"
        );
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * safely closes connection to the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	myConn		connection to database
   * @param 	myStmt		statement used by class
   * @param 	myRs		result set used by class
   */
  private static void close(
    Connection myConn,
    Statement myStmt,
    ResultSet myRs
  )
    throws SQLException {
    if (myRs != null) {
      myRs.close();
    }

    if (myStmt != null) {}

    if (myConn != null) {
      myConn.close();
    }
  }

  /**
   * safely closes statement and its associated result set
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	myStmt		statement used by class
   * @param 	myRs		result set used by class
   */
  private void close(Statement myStmt, ResultSet myRs) throws SQLException {
    close(null, myStmt, myRs);
  }

  /*
  public static void main(String[] args) throws Exception {
    RentalDatabaseObject dao = new RentalDatabaseObject();
    System.out.println(dao.getAllUsers());
    // System.out.println(dao.searchUsername("moussavifan"));
    // System.out.println(dao.searchProperties("apartment", 1, 1, "NE", true));
    // System.out.println(dao.getAllProperties());
    // dao.updatePropertyStatus();
    // dao.changePropertyStatus("111 North ST", "cancelled");
  }*/

  /**
   * gets all subscriptions stored in the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @return 	list of subscriptions
   */
  public List<Subscription> getAllSubscriptions() throws SQLException {
    List<Subscription> list = new ArrayList<>();

    Statement query = null;
    ResultSet results = null;

    try {
      query = myConn.createStatement();
      results = query.executeQuery("SELECT * FROM subscriptions");

      while (results.next()) {
        Subscription tempSub = convertRowToSubscription(results);
        list.add(tempSub);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets a subscription with a specific id from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @return 	subscription data
   */
  public Subscription getSubscription(int id) throws Exception {
    Subscription subscription = null;
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM subscriptions WHERE id = ?");
      query.setInt(1, id);

      results = query.executeQuery();
      while (results.next()) {
        subscription = convertRowToSubscription(results);
      }
      return subscription;
    } finally {
      close(query, results);
    }
  }

  /**
   *
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	property 	property data
   */
  public void notifySubscription(Property property) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      String tempquery = "SELECT * FROM subscriptions WHERE";
      tempquery += " (type = 'any' OR type = '" + property.getType() + "')";
      tempquery += " AND bedrooms <= " + property.getBedrooms();
      tempquery += " AND bathrooms <= " + property.getBathrooms();
      tempquery +=
        " AND (quadrant = 'any' OR quadrant = '" +
        property.getQuadrant() +
        "')";
      int furnishedint = 0;
      if (property.isFurnished()) furnishedint = 1;
      tempquery += " AND furnished <= " + furnishedint;

      System.out.println(tempquery);
      query = myConn.prepareStatement(tempquery);

      results = query.executeQuery();

      while (results.next()) {
        Subscription tempSub = convertRowToSubscription(results);
        enterNotification(
          tempSub.getId(),
          property.getId(),
          tempSub.getRenter()
        );
      }
    } finally {
      close(query, results);
    }
  }

  private void pruneNotifications(Property property) throws SQLException {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "DELETE FROM notifications WHERE listingid = " + property.getId()
        );
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  private void pruneNotifications(Subscription subscription)
    throws SQLException {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "DELETE FROM notifications WHERE subscriptionid = " +
          subscription.getId()
        );
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * converts subscription data received from the database into a Subscription object
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	set			raw subscription data
   * @return 	subscription data stored in Subscription object
   */
  private Subscription convertRowToSubscription(ResultSet set)
    throws SQLException {
    int id = set.getInt("id");
    String renter = set.getString("renter");
    String type = set.getString("type");
    int bedrooms = set.getInt("bedrooms");
    int bathrooms = set.getInt("bathrooms");
    int furnished = set.getInt("furnished");
    boolean furnishedBool = false;
    if (furnished == 1) furnishedBool = true;
    String quadrant = set.getString("quadrant");

    Subscription tempSub = new Subscription(
      id,
      renter,
      type,
      bedrooms,
      bathrooms,
      furnishedBool,
      quadrant
    );

    return tempSub;
  }

  /**
   * deletes a specific subscription from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	id		subscription id
   */
  public void deleteSubscription(int id) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;
    pruneNotifications(getSubscription(id));
    try {
      query =
        myConn.prepareStatement("DELETE FROM subscriptions WHERE id = " + id);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all notifications associated with a specific renter from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	renter		renter's username
   * @return 	list of notifications data
   */
  public List<Notification> getRentersNotifications(String renter)
    throws Exception {
    List<Notification> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM notifications WHERE renter = ?");
      query.setString(1, renter);

      results = query.executeQuery();
      while (results.next()) {
        Notification tempNotification = convertRowToNotification(results);
        list.add(tempNotification);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * converts notification data received from the database to a notifications object
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	set		raw notification data
   * @return 	notification data
   */
  private Notification convertRowToNotification(ResultSet set)
    throws SQLException {
    int subscriptionid = set.getInt("subscriptionid");
    int listingid = set.getInt("listingid");
    String renter = set.getString("renter");

    Notification tempNotification = new Notification(
      subscriptionid,
      listingid,
      renter
    );

    return tempNotification;
  }

  /**
   * deletes a specific notification from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	subscriptionid		notification id
   * @param 	address				property id
   * @param 	renter				renter's username
   */
  public void deleteNotification(
    int subscriptionid,
    String address,
    String renter
  )
    throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "DELETE FROM notifications WHERE subscriptionid = ? AND listingid = ? AND renter = ?"
        );
      query.setInt(1, subscriptionid);
      query.setInt(2, getProperty(address).getId());
      query.setString(3, renter);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * inserts a new notification into the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	subscriptionid		notification id
   * @param 	listingid			property id
   * @param 	renter				renter's username
   */
  public void enterNotification(
    int subscriptionid,
    int listingid,
    String renter
  )
    throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "INSERT INTO notifications (subscriptionid, listingid, renter) VALUES (?, ?, ?)"
        );
      query.setInt(1, subscriptionid);
      query.setInt(2, listingid);
      query.setString(3, renter);
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * Enter dummy logs when with the given database to populate the logs database.
   * Any exisintf properties and their status will be logged for the current date.
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   */
  public void initializeLogs() throws Exception {
    List<Property> list = getAllProperties();
    for (Property p : list) {
      if (getNumberOfPropertyLogs(p) == 0) addLog(p);
    }
  }

  /**
   * Add a log to the property logs to check for the current period.
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	property	property data
   */
  private void addLog(Property property) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "INSERT INTO propertylogs (listingid, statuschange, logdate) VALUES (?, ?, ?)"
        );
      query.setInt(1, property.getId());
      query.setString(2, property.getStatus());
      query.setString(3, java.time.LocalDate.now().toString());
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * add a log to the logs database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	property		property data
   * @param 	statuschange	new status
   */
  private void addLog(Property property, String statuschange) throws Exception {
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "INSERT INTO propertylogs (listingid, statuschange, logdate) VALUES (?, ?, ?)"
        );
      query.setInt(1, property.getId());
      query.setString(2, statuschange);
      query.setString(3, java.time.LocalDate.now().toString());
      System.out.println(query.toString());
      int rowcount = query.executeUpdate();
      System.out.println("Success - " + rowcount + " rows affected.");
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all logs of a specific property from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	property	property data
   * @return
   */
  private int getNumberOfPropertyLogs(Property property) throws Exception {
    int number = 0;
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "SELECT * FROM propertylogs WHERE listingid = ?"
        );
      query.setInt(1, property.getId());

      results = query.executeQuery();
      while (results.next()) {
        number++;
      }
      return number;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all period logs with specific status change and length of period from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	statuschange	latest status
   * @param 	perioddays		length of period
   * @return 	list of property data
   */
  public List<Property> getPeriodLogs(String statuschange, int perioddays)
    throws Exception {
    List<Property> list = new ArrayList<>();

    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement(
          "SELECT * FROM propertylogs WHERE statuschange = ? AND logdate >= ?"
        );
      query.setString(1, statuschange);
      query.setString(
        2,
        java.time.LocalDate.now().minusDays(perioddays).toString()
      );

      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = getProperty(results.getInt("listingid"));
        list.add(tempProperty);
      }
      return list;
    } finally {
      close(query, results);
    }
  }

  /**
   * gets all properties with specific status from the database
   *
   * @throws 	SQLException For handling exceptions regarding MySQL
   * @param 	status		property status
   * @return 	list of property data
   */
  public List<Property> getProperties(String status) throws Exception {
    List<Property> list = new ArrayList<>();
    PreparedStatement query = null;
    ResultSet results = null;

    try {
      query =
        myConn.prepareStatement("SELECT * FROM properties WHERE status = ?");
      query.setString(1, status);

      results = query.executeQuery();
      while (results.next()) {
        Property tempProperty = convertRowToProperty(results);
        list.add(tempProperty);
      }
      return list;
    } finally {
      close(query, results);
    }
  }
}
