## 1. Extract Method

### Location
`Core/Clients/RegisteredClient.java`

### Technique Applied
**Extract Method** - Extracted duplicated password hashing logic into a separate private method.

### Problem Identified
The password hashing code using SHA-256 was duplicated in three different locations within the `RegisteredClient` class:
1. Constructor (lines 33-39)
2. `checkPassword()` method (lines 52-62)
3. `updatePassword()` method (lines 72-78)

Each location contained identical code for creating a MessageDigest instance, hashing the password, and handling exceptions.

### Solution
Created a private method `hashPassword(String password)` that encapsulates all password hashing logic. All three locations now call this method instead of duplicating the code.

### Code Changes
See Github https://github.com/Viki-Lauvrys/SE-temp commit 1

### Benefits
- **Eliminates code duplication**: Hashing logic is now in a single location (DRY principle)
- **Improves maintainability**: Algorithm changes only require modifying one method
- **Enhances readability**: Methods are shorter and more focused

---

## 2. Move Behaviour Close to the Data

### Location
`Core/Orders.java` - `requestOrderCancel()` method

### Technique Applied
**Move Behaviour Close to the Data** (also known as Move Method) - Refactored the method to use the appropriate data structure that was already available in the class.

### Problem Identified
The `requestOrderCancel(int oid)` method was using an inefficient O(n) linear search through the `m_orders` list to find an order by ID, even though the class already maintained a `m_id_order` HashMap specifically designed for O(1) lookups by order ID. Other methods in the same class (`getOrder()` and `getOrderStatus()`) were already using this HashMap correctly.

### Solution
Refactored `requestOrderCancel()` to use the `m_id_order` HashMap for direct lookup instead of iterating through the list.

### Code Changes
See Github https://github.com/Viki-Lauvrys/SE-temp commit 2

### Benefits
- **Better performance**: Changed from O(n) linear search to O(1) HashMap lookup
- **Consistency**: Follows the same pattern as other methods in the class
- **Uses appropriate data structure**: Leverages the HashMap designed for this purpose

---

## 3. Encapsulate Field

### Location
`Core/Clients/Clients.java`

### Technique Applied
**Encapsulate Field** - Changed package-private fields to private fields to properly encapsulate the internal data structures.

### Problem Identified
The `Clients` class had two fields (`m_clients` and `m_all_clients`) that were declared without an access modifier, making them package-private. This meant any class within the same package could directly access and modify these internal data structures, violating encapsulation principles and potentially leading to data integrity issues.

### Solution
Changed both fields from package-private to private, ensuring controlled access through public methods only.

### Code Changes
See Github https://github.com/Viki-Lauvrys/SE-temp commit 3

### Benefits
- **Proper encapsulation**: Fields are now private, enforcing data hiding
- **Controlled access**: All access must go through public interface methods
- **Prevents misuse**: External code cannot directly manipulate internal data structures

---

## 4. Transform Self Type Check

### Location
`Core/Order.java` - `requestCancel()` method and `Core/Order_Status.java`

### Technique Applied
**Transform Self Type Check** (also known as Replace Conditional with Polymorphism) - Moved conditional logic based on order status into the `Order_Status` enum itself, using polymorphism to handle status-specific behavior.

### Problem Identified
The `requestCancel()` method in the `Order` class contained a conditional check (`m_status != Order_Status.DELIVERED`) that determined whether an order could be canceled. This conditional logic was checking the type/state of the order and making decisions based on that check. This violates the principle of keeping behavior close to data and makes it harder to extend or modify status-specific behavior.

### Solution
Added a polymorphic method `canCancel()` to the `Order_Status` enum. The `requestCancel()` method now calls this method instead of performing the conditional check.

### Code Changes
See Github https://github.com/Viki-Lauvrys/SE-temp commit 4

### Benefits
- **Replaces conditional with polymorphism**: Status enum handles its own behavior
- **Better encapsulation**: Cancel logic is encapsulated where it belongs
- **Easier to extend**: New status types or rule changes only require enum modifications