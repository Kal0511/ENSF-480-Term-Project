package src.Entities;

/**
 * Notification is an entity class used to store notification data received from the database
 */
public class Notification {

  int subscriptionID;
  int listingID;
  String renter;

	/**
	 * Constructor.
	 *
	 * @param subscriptionID    id of the subscription
	 * @param listingID    		id of the property
	 * @param renter     		username of the renter
	 */
  public Notification(int subscriptionID, int listingID, String renter) {
    this.subscriptionID = subscriptionID;
    this.listingID = listingID;
    this.renter = renter;
  }

  public int getSubscriptionID() {
    return subscriptionID;
  }

  public void setSubscriptionID(int subscriptionID) {
    this.subscriptionID = subscriptionID;
  }

  public int getListingID() {
    return listingID;
  }

  public void setListingID(int listingID) {
    this.listingID = listingID;
  }

  public String getRenter() {
    return renter;
  }

  public void setRenter(String renter) {
    this.renter = renter;
  }
}
