package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.subTest;

import java.sql.SQLException;
import java.util.List;

public interface subTestDAO extends crudDAO<subTest> {
    List<subTest> getAllsubTests(String text) throws SQLException, ClassNotFoundException;


    }
