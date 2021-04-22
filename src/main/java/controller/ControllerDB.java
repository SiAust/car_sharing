package controller;

import database.CarRepository;
import database.CompanyRepository;
import model.Car;
import model.Company;

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

    public ControllerDB(String fileName) {
        db_url += fileName;
        try {
            openConnectionToDB();
            companyRepository = new CompanyRepository(stmt);
            carRepository = new CarRepository(stmt);
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
        return companyRepository.createTable() && carRepository.create();
    }

    // Company table actions
    public boolean createCompany(String fileName) {
        return companyRepository.createCompany(fileName);
    }

    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    // Car table actions
    public boolean addCar(String name, int companyID) {
        return carRepository.addCar(name, companyID);
    }

    public List<Car> getCars(int companyID) {
        return carRepository.getCars(companyID);
    }

}