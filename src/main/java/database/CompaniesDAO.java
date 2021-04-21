package database;

import model.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO {

    boolean createCompany(String name);

    boolean deleteCompany(int id);

    Company getCompany();
    List<Company> getCompanies() throws SQLException;
}
