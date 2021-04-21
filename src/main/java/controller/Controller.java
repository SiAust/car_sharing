package controller;

import database.CompanyRepository;
import view.View;

import java.util.Scanner;

public class Controller {

    CompanyRepository model;
    View view;

    private final Scanner scanner = new Scanner(System.in);

    public Controller(CompanyRepository model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        if (!model.create()) {
            view.printDatabaseCreationError();
            return;
        }
        printMainMenu();
        scanner.close();
    }

    private void printMainMenu() {
        int input = 1;
        do {
            view.printMainMenu();
            try {
                input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        printManagerMenu();
                        break;
                    case 0:
                        break;
                }
            } catch (NumberFormatException e) {
                view.printInvalidInput();
            }
        }  while (input != 0);
    }

    private void printManagerMenu() {
        int input = 1;
         do {
            view.printManagerMenu();
            try {
                input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        printCompanyList();
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

    private void createCompany() {
        view.printCreateCompany();
        if (model.createCompany(scanner.nextLine())) {
            view.printCompanyCreatedSuccess();
        } else {
            view.printCompanyCreationFailed();
        }
    }

    private void printCompanyList() {
        view.printCompanyList(model.getCompanies());
    }
}
