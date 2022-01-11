package src.Entities;

/**
 * Subscription is an entity class used to store a list of subscription data
 */
public class Subscription {

  private int id;
  private String renter;
  private String type;
  private int bedrooms;
  private int bathrooms;
  private boolean furnished;
  private String quadrant;

	/**
	 * Constructor.
	 *
	 * @param id    		id of the property
	 * @param renter     	renter's username
	 * @param type			type of property
	 * @param bedrooms		number of bedrooms
	 * @param bathrooms		number of bathrooms
	 * @param quadrant		which quadrant the property is located in
	 * @param furnished		if the property should be furnished
	 */
  public Subscription(
    int id,
    String renter,
    String type,
    int bedrooms,
    int bathrooms,
    boolean furnished,
    String quadrant
  ) {
    this.id = id;
    this.renter = renter;
    this.type = type;
    this.bedrooms = bedrooms;
    this.bathrooms = bathrooms;
    this.furnished = furnished;
    this.quadrant = quadrant;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRenter() {
    return renter;
  }

  public void setRenter(String renter) {
    this.renter = renter;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getBedrooms() {
    return bedrooms;
  }

  public void setBedrooms(int bedrooms) {
    this.bedrooms = bedrooms;
  }

  public int getBathrooms() {
    return bathrooms;
  }

  public void setBathrooms(int bathrooms) {
    this.bathrooms = bathrooms;
  }

  public boolean isFurnished() {
    return furnished;
  }

  public void setFurnished(boolean furnished) {
    this.furnished = furnished;
  }

  public String getQuadrant() {
    return quadrant;
  }

  public void setQuadrant(String quadrant) {
    this.quadrant = quadrant;
  }
}
