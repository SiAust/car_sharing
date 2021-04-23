package database;

import model.Company;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface CompanyDAO {

    boolean createCompany(String name);

    boolean deleteCompany(int id);

    Company getCompany();
    List<Company> getCompanies() throws SQLException;
}
