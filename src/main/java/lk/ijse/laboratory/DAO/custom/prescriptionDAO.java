package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.prescription;

import java.sql.SQLException;

public interface prescriptionDAO extends crudDAO<prescription> {
    int getCount() throws SQLException, ClassNotFoundException;
    prescription SearchPrescriptionById(String id) throws SQLException, ClassNotFoundException;
}
