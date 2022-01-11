package src.Entities;

/**
 * Email is an entity class used to store email data received from the database
 */
public class Email {

  private String sender;
  private String receiver;
  private String message;

	/**
	 * Constructor.
	 *
	 * @param sender      username of sender
	 * @param receiver    username of receiver
	 * @param message     message body
	 */
  public Email(String sender, String receiver, String message) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
