package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.instruction;

import java.sql.SQLException;
import java.util.ArrayList;

public interface instructionDAO extends crudDAO<instruction> {
    ArrayList<String> getInstructions(String id) throws SQLException, ClassNotFoundException;

}
