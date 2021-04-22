package view;

import model.Car;
import model.Company;

import java.util.List;

public class View {

    // Menu output
    public void printMainMenu() {
        System.out.println("1. Log in as a manager\n0. Exit");
    }

    public void printManagerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
    }

    // Company specific output
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

    public void printCreateCompany() {
        System.out.println("\nEnter the company name:");
    }

    public void printCompanyCreatedSuccess() {
        System.out.println("The company was created!");
    }

    public void printCompanyCreationFailed() {
        System.out.println("Failed to create company.");
    }

    public void printCompanyCarMenu(String name) {
        System.out.println("\n'" + name + "' company:\n1. Car list\n2. Create a car\n0. Back");
    }

    // Car specific output
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

    // Error output
    public void printInvalidInput() {
        System.out.println("Invalid input.");
    }

    public void printDatabaseCreationError() {
        System.out.println("Problem creating database. Check command line args. \nExiting.");
    }
}



