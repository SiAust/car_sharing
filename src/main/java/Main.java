import controller.Controller;
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
        Controller controller = new Controller(new CompanyRepository(fileName), new View());
        controller.run();
    }
}
