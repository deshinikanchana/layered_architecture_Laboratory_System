package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.section;

import java.sql.SQLException;

public interface sectionDAO extends crudDAO<section> {
    int getTestCount(String secId) throws SQLException, ClassNotFoundException;

    }
