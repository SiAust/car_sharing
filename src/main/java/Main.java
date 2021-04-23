import controller.Controller;
import controller.ControllerDB;
import database.CompanyRepository;
import view.View;


public class Main {

    public static void main(String[] args) {
        String fileName = "db";
        if (args.length > 0) {
            if (args[0].equals("-databaseFileName")) {
                fileName = args[1];
            }
        }
        Controller controller = new Controller(new ControllerDB(fileName), new View());
        controller.run();
    }
}
