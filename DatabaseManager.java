import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public static void addContact(String name, String phoneNumber, String email, String address) {
        String sql = "INSERT INTO Contacts (Name, PhoneNumber, Email, Address) VALUES (?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.executeUpdate();
            System.out.println("Contact added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding contact: " + e.getMessage());
        }
    }

    public static void updateContact(int id, String name, String phoneNumber, String email, String address) {
        String sql = "UPDATE Contacts SET Name=?, PhoneNumber=?, Email=?, Address=? WHERE ContactID=?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            System.out.println("Contact updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating contact: " + e.getMessage());
        }
    }

    public static void deleteContact(int id) {
        String sql = "DELETE FROM Contacts WHERE ContactID=?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Contact deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting contact: " + e.getMessage());
        }
    }

    public static List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM Contacts";

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacts.add(new Contact(
                        rs.getInt("ContactID"),
                        rs.getString("Name"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getString("Address")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching contacts: " + e.getMessage());
        }
        return contacts;
    }
}
