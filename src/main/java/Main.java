import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static String db_url = "jdbc:h2:./db/";

    // don't use credentials

    public static void main(String[] args) {
        if (args.length > 0) {
            String dbPostfix;
            if (args[0].equals("-databaseFileName")) {
                dbPostfix = args[1];
            } else {
                // in case there is no argument, database path needs filename
                dbPostfix = "db";
            }
            db_url += dbPostfix;
        }
        Connection conn;
        Statement stmt;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(db_url);

            System.out.println("Creating table in database...");
            stmt = conn.createStatement();
            String sql =
                    "CREATE TABLE COMPANY (" +
                    "ID INT," +
                    "NAME VARCHAR(20));";
            stmt.executeUpdate(sql);
            System.out.println("Created table in database...");

            stmt.close();
            conn.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
