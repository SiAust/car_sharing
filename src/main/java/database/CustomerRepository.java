package database;

import model.Customer;

import org.h2.jdbc.JdbcSQLSyntaxErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements CustomerDAO {

    private final Statement stmt;

    public CustomerRepository(Statement stmt) {
        this.stmt = stmt;
    }

    public boolean create() {
        try {
            String sql =
                    "CREATE TABLE CUSTOMER (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(20) UNIQUE NOT NULL," +
                    "RENTED_CAR_ID INT DEFAULT NULL," +
                    "CONSTRAINT fk_car_id FOREIGN KEY (RENTED_CAR_ID)" +
                    "REFERENCES CAR(ID)" +
                    "ON UPDATE CASCADE " +
                    "ON DELETE SET NULL);";
            stmt.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
//            e.printStackTrace();
            if (e instanceof JdbcSQLSyntaxErrorException) {
                return e.getMessage().contains("already exists");
            }
        }
        return false;
    }

    @Override
    public Customer getCustomer() {
        // todo return customer
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // todo return customers
        List<Customer> customers = new ArrayList<>();
        String sql =
                "SELECT * FROM CUSTOMER";
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println(customers.get(0));
        return customers;
    }

    @Override
    public boolean addCustomer(String name) {
        String sql =
                "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) " +
                "VALUES ('" + name + "', NULL);"; // fixme RENTAL_CAR_ID needs to exist!
        try {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCustomer(Customer customer) {
        // todo remove customer
        return false;
    }

    @Override
    public boolean setRentalCar(int customerID, int carID) {
        String sql = "UPDATE CUSTOMER " +
                "SET RENTED_CAR_ID = " + carID +
                " WHERE ID = " + customerID + ";";
        try {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
