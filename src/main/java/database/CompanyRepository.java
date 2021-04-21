package database;

import model.Company;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements CompaniesDAO {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static String db_url = "jdbc:h2:./db/";

    private static Connection conn;
    private static Statement stmt;

    public CompanyRepository(String fileName) {
        db_url += fileName;
    }

    private void openConnectionToDB() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(db_url);
        stmt = conn.createStatement();
    }

    private void closeConnectionToDB() throws SQLException {
        conn.close();
        stmt.close();
    }


    public boolean create() {
        try {
            openConnectionToDB();
            String sql =
                    "CREATE TABLE COMPANY (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(20) NOT NULL UNIQUE);";
            stmt.executeUpdate(sql);
            System.out.println("Created table in database...");

            closeConnectionToDB();
            return true;

        } catch (ClassNotFoundException | SQLException e) {
            if (e instanceof JdbcSQLSyntaxErrorException) {
                return false; // todo handle message
            }
        }
        return false;
    }

    @Override
    public boolean createCompany(String company) {
        String request = "INSERT INTO COMPANY (NAME) VALUES ('" + company + "');";
        try {
            openConnectionToDB();
            stmt.execute(request);
            closeConnectionToDB();
        } catch (SQLException | ClassNotFoundException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCompany(int id) {
        return false; // todo add SQL
    }

    @Override
    public Company getCompany() {
        return null; //todo add SQL
    }

    @Override
    public List<Company> getCompanies() {
        String requestAllCompanies = "SELECT * FROM COMPANY;";
        List<Company> companyList = new ArrayList<>();
        try {
            openConnectionToDB();
            ResultSet resultSet = stmt.executeQuery(requestAllCompanies);
            while (resultSet.next()) {
                companyList.add(new Company(resultSet.getInt("ID"),
                        resultSet.getString("NAME")));
            }
            closeConnectionToDB();
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                return companyList;
            }
        }
        return companyList;
    }
}
