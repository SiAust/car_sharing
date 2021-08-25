package controller;

import database.CarRepository;
import database.CompanyRepository;
import database.CustomerRepository;
import model.Car;
import model.Company;
import model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/** Handles calls to Company parent and Car child relational tables. Declares and initializes
 *  {@code Connection conn} and {@code Statement stmt} before passing to these objects. */
public class ControllerDB {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static String db_url = "jdbc:h2:./db/";

    private static Connection conn;
    private static Statement stmt;

    private CompanyRepository companyRepository;
    private CarRepository carRepository;
    private CustomerRepository customerRepository;

    public ControllerDB(String fileName) {
        db_url += fileName;
        try {
            openConnectionToDB();
            companyRepository = new CompanyRepository(stmt);
            carRepository = new CarRepository(stmt);
            customerRepository = new CustomerRepository(stmt);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void openConnectionToDB() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(db_url);
        stmt = conn.createStatement();
    }

    public void closeConnectionToDB() throws SQLException {
        conn.close();
        stmt.close();
    }

    public boolean createTables() {
        return companyRepository.createTable() && carRepository.create() && customerRepository.create();
    }

    // Company table actions
    public boolean createCompany(String fileName) {
        return companyRepository.createCompany(fileName);
    }

    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    public Company getCompany(int companyID) {
        return companyRepository.getCompany(companyID);
    }
    // Car table actions
    public boolean addCar(String name, int companyID) {
        return carRepository.addCar(name, companyID);
    }

    public List<Car> getCars(int companyID) {
        return carRepository.getCars(companyID);
    }

    public Car getCar(int carID) {
        return carRepository.getCar(carID); // todo update to "available" cars
    }

    public boolean companyHasCarsAvailable(Company company) {
        return carRepository.hasRentalCarsAvailable(company); // todo implement
    }

    // Customer table actions
    public boolean addCustomer(String customer) {
        return customerRepository.addCustomer(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepository.getAllCustomers();
    }

    public boolean setCustomerRentalCar(int customerID, Car car) {
        return customerRepository.setRentalCar(customerID, car.getId())
                && carRepository.setCarIsRented(true, car.getId());
    }

    public boolean returnRentalCar(Customer customer) {
        return customerRepository.setRentalCar(customer.getId(), 0)
                && carRepository.setCarIsRented(false, customer.getRentedCarID());
    }
}
