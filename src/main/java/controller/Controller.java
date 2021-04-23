package controller;

import model.Company;
import view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Controller {

    ControllerDB model;
    View view;

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
                    case 0:
                        break;
                }
            } catch (NumberFormatException e) {
                view.printInvalidInput();
            }
        } while (input != 0);
    }

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
