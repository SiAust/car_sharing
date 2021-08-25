package controller;

import model.Car;
import model.Company;
import model.Customer;
import view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Controller {

    ControllerDB model;
    View view;

    private Customer currentCustomer;

    private final Scanner scanner = new Scanner(System.in);

    public Controller(ControllerDB model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        if (!model.createTables()) {
            view.printDatabaseCreationError();
            return;
        }
        mainMenu();
        scanner.close();
        // try to close the database connection before exiting app
        try {
            model.closeConnectionToDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Menu methods
    private void mainMenu() {
        int input = 1;
        do {
            view.printMainMenu();
            try {
                input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        managerMenu();
                        break;
                    case 2:
                        // customer menu
                        customerMenu();
                        break;
                    case 3:
                        // create customer
                        createCustomer();
                        break;
                    case 0:
                        break;
                }
            } catch (NumberFormatException e) {
                view.printInvalidInput();
            }
        } while (input != 0);
    }

    /* Customer menus */

    private void customerMenu() {
        List<Customer> customers = model.getCustomers();
        if (customers.size() > 0) {
            int input = 0;
            do {
                view.printCustomerLoggedInMenu(customers);
                input = Integer.parseInt(scanner.nextLine());
                /* check input matches a customer */
                if (input - 1 <= customers.size() && input - 1 >= 0) {
                    /* set current customer */
                    currentCustomer = customers.get(input - 1);
//                    System.out.println(currentCustomer);
                    customerOptions();
                } else {
                    // todo no customer match
                }
            } while (input != 0);

        } else {
            view.printCustomerListEmpty();
        }
        currentCustomer = null;
    }

    private void customerOptions() {
        int input = 1;
        do {
            view.printCustomerOptions();
            input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    // rent a car
                    customerRentCar();
                    break;
                case 2:
                    // return a rented car
                    customerReturnRentedCar();
                    break;
                case 3:
                    // my rented car
                    customerMyRentedCar();
                    break;
            }
        } while (input != 0);
    }

    private void customerRentCar() {
        /* Check customer has already rented a car */
        if (currentCustomer.getRentedCarID() != 0) {
            view.printCarAlreadyRented();
        } else  {
            int input;
            List<Company> companies = model.getCompanies();
            view.printCompanyMenu(companies);
            input = Integer.parseInt(scanner.nextLine());
            if (input <= companies.size() && input > 0) {
                companyRentalCars(companies.get(input - 1));
            } else {
                view.printInvalidInput();
            }
        }

    }

    private void companyRentalCars(Company company) {
        /* Check if manufacturer has available cars to rent */
        if (model.companyHasCarsAvailable(company)) {
            List<Car> cars = model.getCars(company.getId());
            /* Filter the cars which are rented */
            List<Car> filteredCars = cars.stream()
                    .filter(Predicate.not(Car::isRented))
                    .collect(Collectors.toList());
            view.printAvailableRentalCars(filteredCars);
            int input = Integer.parseInt(scanner.nextLine());
            if (input - 1 <= filteredCars.size() && input > 0) {
                Car carRented = filteredCars.get(input - 1);
                if (model.setCustomerRentalCar(currentCustomer.getId(), carRented)) {
                    // success message
                    view.customerRentedCar(carRented);
                    currentCustomer.setRentedCarID(carRented.getId());
                } else {
                    // failure message
                    view.printErrorRentingCar();
                }
            } else if (input - 1 > filteredCars.size()){
                view.printInvalidInput();
            }
        } else {
            view.printNoCarsAvailable(company);
        }
    }

    private void customerReturnRentedCar() {
        int rentedCarID = currentCustomer.getRentedCarID();
        if (rentedCarID != 0) {
            if (model.returnRentalCar(currentCustomer)) {
                view.printIsCarReturned(true);
                currentCustomer.setRentedCarID(0);
            } else {
                view.printIsCarReturned(false);
            }
        } else {
            view.printNoCarRented();
        }
    }

    private void customerMyRentedCar() {
        int rentedCardId = currentCustomer.getRentedCarID();
        if (rentedCardId == 0) {
            // RENTED_CAR_ID is 0 == SQL NULL
            view.printNoCarRented();
//            System.out.println(currentCustomer);
        } else {
            Car currentRentedCar = model.getCar(rentedCardId);
//            System.out.println(currentRentedCar);
            Company carRentalCompany = model.getCompany(currentRentedCar.getCompanyID());
            view.printRentedCar(currentRentedCar, carRentalCompany);
        }
    }

    private void createCustomer() {
        view.printPromptCustomerName();
        boolean addedCustomer = (model.addCustomer(scanner.nextLine()));
        view.printCustomerCreated(addedCustomer);
    }

    /* Manager menus */

    private void managerMenu() {
        int input = 1;
        do {
            view.printManagerMenu();
            try {
                input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        companyMenu();
                        break;
                    case 2:
                        createCompany();
                        break;
                    case 0:
                        break;
                }
            } catch (NumberFormatException e) {
                view.printInvalidInput();
            }
        } while (input != 0);
    }

    private void companyMenu() {
        List<Company> companies = model.getCompanies();
        if (companies.size() == 0) {
            view.printCompanyMenu(companies); // todo split methods? Overload?
            return;
        }
        view.printCompanyMenu(companies);
        try {
            int input = Integer.parseInt(scanner.nextLine());
            if (input <= companies.size() && input != 0) {
                companySpecificMenu(companies.get(input - 1));
            } else if (input > companies.size()) { // number is greater than companies listed
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            view.printInvalidInput();
        }
    }

    private void companySpecificMenu(Company company) {
        int input = 1;
        do {
            view.printCompanySpecificMenu(company.getName());
            try {
                input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        view.printCarsList(model.getCars(company.getId()));
                        break;
                    case 2:
                        view.printCreateCar();
                        view.printIsCarCreated(model.addCar(scanner.nextLine(), company.getId()));
                        break;
                    case 0:
                        break;
                }
            } catch (NumberFormatException e) {
                view.printInvalidInput();
            }
        } while (input != 0);
    }

    // Company
    private void createCompany() {
        view.printCreateCompany();
        if (model.createCompany(scanner.nextLine())) {
            view.printCompanyCreatedSuccess();
        } else {
            view.printCompanyCreationFailed();
        }
    }
}
