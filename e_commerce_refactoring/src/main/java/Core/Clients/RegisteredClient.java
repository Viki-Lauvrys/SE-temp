package Core.Clients;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import Core.Order;
import Core.Order_Status;
import Core.Orders;

/**
 * Class representing a registered client
 *
 */
public class RegisteredClient extends Client {

	private byte[] m_password;
	
	private ArrayList<Integer> m_order_history;
	private Mailing_Preferences m_mailing_preferences;
	
	/**
	 * Hash a plain-text password using SHA-256
	 * 
	 * @param password	Plain-text password to hash
	 * @return	Hashed password as byte array, or null if hashing fails
	 */
	private byte[] hashPassword(String password) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			return digest.digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Something went wrong");
			return null;
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param name	Client's name
	 * @param address	Client's address
	 * @param password	Client's password (plain-text, gets hashed in the function)
	 * @param mailing_preferences	Client's mail preferences
	 */
	public RegisteredClient(String name, Address address, String password, Mailing_Preferences mailing_preferences) {
		super(name, address);
		
		m_password = hashPassword(password);
		
		m_order_history = new ArrayList<Integer>();
		m_mailing_preferences = mailing_preferences;
	}
	
	/**
	 * Check if an entered password matches password from user
	 * 
	 * @param password	Entered password
	 * @return	boolean
	 */
	public boolean checkPassword(String password) {
		byte[] hashedPassword = hashPassword(password);
		if (hashedPassword != null && m_password.equals(hashedPassword))
			return true;
		else
			return false;
	}
	
	/**
	 * Update the user's password
	 * 
	 * @param password	New password (plain-text)
	 */
	public void updatePassword(String password) {
		m_password = hashPassword(password);
	}
	
	/**
	 * Update the client's mail preferences
	 * 
	 * @param preferences	The new preferences from the client
	 */
	public void updateMailPreferences(Mailing_Preferences preferences) {
		m_mailing_preferences = preferences;
	}
	
	/**
	 * Returns the mailing preferences from the client
	 * 
	 * @return
	 */
	public Mailing_Preferences getMailPreferences() {
		return m_mailing_preferences;
	}
	
	/**
	 * Add orderID to the list of placed orders
	 * 
	 * @param orderID	ID from new order
	 */
	public void addOrder(Integer orderID) {
		m_order_history.add(orderID);
	}
	
	/**
	 * Returns a list of orders containing all orders who are not yet delivered
	 * 
	 * @param orders	Object representing all orders present in the system
	 * @return	List of Orders
	 */
	public ArrayList<Order> getOpenOrders(Orders orders) {
		ArrayList<Order> open_orders = new ArrayList<Order>();
		for (Integer oid : m_order_history) {
			if (orders.getOrderStatus(oid) == Order_Status.STARTED)
				open_orders.add(orders.getOrder(oid));
		}
		return open_orders;
	}
	
	/**
	 * Returns a list of orders containing all orders that have been delivered
	 * 
	 * @param orders	Object representing all orders present in the system
	 * @return	List of Orders
	 */
	public ArrayList<Order> getDeliveredOrders(Orders orders) {
		ArrayList<Order> delivered_orders = new ArrayList<Order>();
		for (Integer oid : m_order_history) {
			if (orders.getOrderStatus(oid) == Order_Status.DELIVERED)
				delivered_orders.add(orders.getOrder(oid));
		}
		return delivered_orders;		
	}
	
	
}
