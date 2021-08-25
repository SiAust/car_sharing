package database;

import model.Customer;

import java.util.List;

public interface CustomerDAO {

    Customer getCustomer();

    List<Customer> getAllCustomers();

    boolean addCustomer(String name);

    boolean removeCustomer(Customer customer);

    boolean setRentalCar(int customerID, int carID);
}
