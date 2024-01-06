package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.designation;

import java.sql.SQLException;

public interface designationDAO extends crudDAO<designation> {
    int getEmpCount(String jobId) throws SQLException, ClassNotFoundException;
}