package Core;

public enum Order_Status {
	STARTED, PAYED, DELIVERED, CANCELED;
	
	/**
	 * Check if an order with this status can be canceled
	 * 
	 * @return true if the order can be canceled, false otherwise
	 */
	public boolean canCancel() {
		return this != DELIVERED;
	}
}
