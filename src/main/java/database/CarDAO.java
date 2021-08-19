package database;

import model.Car;
import model.Company;

import java.util.List;

public interface CarDAO {

    Car getCar(int carID);

    List<Car> getCars(int companyID);

    boolean addCar(String name, int companyID);

    boolean removeCar(Car car);
}
