package database;

import model.Car;
import model.Company;

import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements CarDAO {

    private final Statement stmt;

    public CarRepository(Statement stmt) {
        this.stmt = stmt;
    }

    public boolean create() {
        try {
            String sql =
                    "CREATE TABLE CAR (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(20) NOT NULL UNIQUE," +
                    "COMPANY_ID INT NOT NULL," +
                    "IS_RENTED BOOLEAN DEFAULT FALSE," +
                    "CONSTRAINT fk_company_id FOREIGN KEY (COMPANY_ID)" +
                    "REFERENCES COMPANY(ID)" +
                    "ON UPDATE CASCADE " +
                    "ON DELETE SET NULL);";
            stmt.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
//            e.printStackTrace(System.out);
            if (e instanceof JdbcSQLSyntaxErrorException) {
                return e.getMessage().contains("already exists");
            }
        }
        return false;
    }

    @Override
    public Car getCar(int carID) {
        String sql = "SELECT * FROM CAR WHERE ID = " + carID + ";";
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
//            System.out.println(resultSet);
            Car car = null;
            if (resultSet.next()) {
                car = new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID"),
                        resultSet.getBoolean("IS_RENTED"));
            }
            return car;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> getCars(int companyID) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyID + ";";
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID"),
                        resultSet.getBoolean("IS_RENTED")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cars;
    }

    @Override
    public boolean addCar(String name, int companyID) {
        String sql =
                "INSERT INTO CAR (NAME, COMPANY_ID) " +
                "VALUES ('" + name + "', " + companyID + ");";
        try {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCar(Car car) {
        return false;
    }

    @Override
    public boolean setCarIsRented(boolean isRented, int carID) {
        String sql =
                "UPDATE CAR " +
                "SET IS_RENTED = " + isRented + " " +
                "WHERE ID = " + carID + ";";
        try {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasRentalCarsAvailable(Company company) {
        String sql =
                "SELECT * FROM CAR " +
                "WHERE COMPANY_ID = " + company.getId() +
                " AND IS_RENTED = FALSE";
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
//            System.out.println(resultSet);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
