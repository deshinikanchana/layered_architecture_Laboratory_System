package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.collectingCenter;

import java.sql.SQLException;
import java.util.List;

public interface collectingCenterDAO extends crudDAO<collectingCenter> {
     int getSampleCount(String CcId) throws SQLException, ClassNotFoundException;
}
