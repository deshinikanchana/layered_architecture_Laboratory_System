package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.stockUsage;

import java.sql.SQLException;
import java.util.List;

public interface stockUsageDAO extends crudDAO<stockUsage> {
    List<stockUsage> getUsages(String testId) throws SQLException, ClassNotFoundException;
}
