package database;

import model.Company;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements CompanyDAO {

    private final Statement stmt;

    public CompanyRepository(Statement stmt) {
        this.stmt = stmt;
    }

    public boolean createTable() {
        try {
            String sql =
                    "CREATE TABLE COMPANY (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(20) NOT NULL UNIQUE);";
            stmt.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
//            e.printStackTrace(System.out);
            if (e instanceof JdbcSQLSyntaxErrorException) {
                if (((JdbcSQLSyntaxErrorException) e).getMessage().contains("already exists")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean createCompany(String company) {
        String request = "INSERT INTO COMPANY (NAME) VALUES ('" + company + "');";
        try {
            stmt.execute(request);
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCompany(int id) {
        return false; // todo add SQL
    }

    @Override
    public Company getCompany(int companyID) {
        String sql = "SELECT * FROM COMPANY WHERE ID = " + companyID + ";";
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                return new Company(resultSet.getInt("ID"),
                        resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Company> getCompanies() {
        String requestAllCompanies = "SELECT * FROM COMPANY;";
        List<Company> companyList = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery(requestAllCompanies);
            while (resultSet.next()) {
                companyList.add(new Company(resultSet.getInt("ID"),
                        resultSet.getString("NAME")));
            }
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                return companyList;
            }
        }
        return companyList;
    }
}
