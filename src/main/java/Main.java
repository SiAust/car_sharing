import controller.Controller;
import database.CompanyRepository;
import view.View;


public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            String fileName;
            if (args[0].equals("-databaseFileName")) {
                fileName = args[1];
            } else {
                // in case there is no argument, database path needs filename
                fileName = "db";
            }

            Controller controller = new Controller(new CompanyRepository(fileName), new View());
            controller.run();
        }
    }
}
