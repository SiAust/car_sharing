package view;

import model.Car;
import model.Company;
import model.Customer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class View {

    // Menu output
    /**
     * Main menu displays "Log in as a manager", "Exit"
     * */
    public void printMainMenu() {
        System.out.println("1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
    }

    /**
     * Manager menu, "Company List", "Create company", "Back"
     * */
    public void printManagerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
    }

    // Company specific output
    /**
     * Company menu - "Choose the company", "n. 'company name'", "Back".
     * If there are no companies - "The company list is empty!".
     * */
    public void printCompanyMenu(List<Company> companies) {
        if (companies.size() != 0) {
            System.out.println("\nChoose the company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, companies.get(i).getName());
            }
            System.out.println("0. Back");
        } else {
            System.out.println("\nThe company list is empty!");
        }
    }

    /**
     * Displays company name, "Car list", "Create a car", "Back".
     * */
    public void printCompanySpecificMenu(String name) {
        System.out.println("\n'" + name + "' company:\n1. Car list\n2. Create a car\n0. Back");
    }

    public void printCreateCompany() {
        System.out.println("\nEnter the company name:");
    }

    public void printCompanyCreatedSuccess() {
        System.out.println("The company was created!");
    }

    public void printCompanyCreationFailed() {
        System.out.println("Failed to create company.");
    }

    // Car specific output
    /** Print the list of cars for a company, if any, otherwise "The car list is empty!".
     * */
    public void printCarsList(List<Car> cars) {
        if (cars.size() > 0) {
            System.out.println("\nCar list");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println((i + 1) + ". " + cars.get(i).getName());
            }
        } else {
            System.out.println("\nThe car list is empty!");
        }
    }

    public void printAvailableRentalCars(List<Car> cars) {
        if (cars.size() > 0) {
            System.out.println("\nChoose a car:");
            List<Car> filteredCars = cars.stream()
                    .filter(Car::isRented)
                    .collect(Collectors.toList());
            for (int i = 0; i < filteredCars.size(); i++) {
                System.out.println((i + 1) + ". " + filteredCars.get(i).getName());
            }
            System.out.println("0. Back");
        } else {
            System.out.println("\nThe car list is empty!");
        }
    }

    public void customerRentedCar(Car car) {
        System.out.println("\nYou rented '" + car.getName() + "'" );
    }

    public void printCreateCar() {
        System.out.println("Enter the car name:");
    }

    public void printIsCarCreated(boolean isCreated) {
        if (isCreated) {
            System.out.println("The car was added!");
        } else {
            System.out.println("Failed to add car.");
        }
    }

    // Customer menu tree
    public void printCustomerLoggedInMenu(List<Customer> customers) {
        final StringBuilder menuString = new StringBuilder("Choose a customer:\n");
        for (int i = 0; i < customers.size(); i++) {
            menuString.append(String.format("%d. %s\n",i + 1, customers.get(i).getName()));
        }
        menuString.append("0. Back");
        System.out.println(menuString);
    }

    public void printCustomerListEmpty() {
        System.out.println("The customer list is empty!\n");
    }

    public void printCustomerOptions() {
        System.out.println("1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back");
    }

    public void printNoCarRented() {
        System.out.println("You didn't rent a car!\n");
    }

    public void printCarAlreadyRented() {
        System.out.println("You've already rented a car!\n");
    }

    public void printRentedCar(Car car, Company company) {
        System.out.printf("Your rented car:\n%s\nCompany:\n%s\n%n",
                car.getName(), company.getName());
    }

    public void printNoCarsAvailable(Company company) {
        System.out.printf("No cars available in the '%s' company\n", company.getName());
    }

    public void printPromptCustomerName() {
        System.out.println("Enter the customer name:");
    }

    public void printCustomerCreated(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("The customer was added!\n");
        } else {
            System.out.println("The customer creation failed!");
        }
    }

    // Error output
    public void printInvalidInput() {
        System.out.println("Invalid input.");
    }

    public void printDatabaseCreationError() {
        System.out.println("Problem creating database. Check command line args. \nExiting.");
    }

    public void printErrorRentingCar() {
        System.out.println("Problem renting car.");
    }
}



