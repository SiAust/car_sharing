package view;

import model.Company;

import java.util.List;

public class View {

    public void printMainMenu() {
        System.out.println("1. Log in as manager\n0. Exit");
    }

    public void printManagerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
    }

    public void printCompanyList(List<Company> companies) {
        if (companies.size() != 0) {
            System.out.println("\nCompany list:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.printf("%s\n", companies.get(i));
            }
        } else {
            System.out.println("\nThe company list is empty!\n");
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

    public void printInvalidInput() {
        System.out.println("Invalid input.");
    }
}
