import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost/store_db";
    private static final String DB_USER = "your_database_username";
    private static final String DB_PASSWORD = "your_database_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer() {
    }

    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


public class CustomerDAO {

    public static void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // Handle the exception or rethrow it to the calling method
            System.err.println("Error occurred while adding customer: " + ex.getMessage());
            throw ex;
        }
    }

    // Get a customer by ID
    public static Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToCustomer(resultSet);
                }
            }
        } catch (SQLException ex) {
            // Handle the exception or rethrow it to the calling method
            System.err.println("Error occurred while getting customer: " + ex.getMessage());
            throw ex;
        }
        return null;
    }

    // Update customer details
    public static void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setInt(4, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // Handle the exception or rethrow it to the calling method
            System.err.println("Error occurred while updating customer: " + ex.getMessage());
            throw ex;
        }
    }

    // Delete a customer from the database
    public static void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // Handle the exception or rethrow it to the calling method
            System.err.println("Error occurred while deleting customer: " + ex.getMessage());
            throw ex;
        }
    }

    // Helper method to convert ResultSet to Customer object
    private static Customer resultSetToCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhone(resultSet.getString("phone"));
        return customer;
    }

    //Main Function
    public static void main(String[] args) {
        // Sample customer data
        Customer customer1 = new Customer();
        customer1.setName("Varun Saini");
        customer1.setEmail("Varun@gmail.com");
        customer1.setPhone("1122334455");

        try {
            // Adding a new customer
            addCustomer(customer1);
            System.out.println("Customer added successfully!");

            // Retrieve a customer by ID
            int customerId = customer1.getId(); // Replace with the actual customer ID to retrieve
            Customer retrievedCustomer = getCustomerById(customerId);
            if (retrievedCustomer != null) {
                System.out.println("Retrieved customer: " + retrievedCustomer.getName());
            } else {
                System.out.println("Customer not found!");
            }

            // Update customer details
            retrievedCustomer.setEmail("john.doe.updated@example.com");
            updateCustomer(retrievedCustomer);
            System.out.println("Customer updated successfully!");

            // Delete a customer
            int customerIdToDelete = 1; // Replace with the actual customer ID to delete
            deleteCustomer(customerIdToDelete);
            System.out.println("Customer deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}