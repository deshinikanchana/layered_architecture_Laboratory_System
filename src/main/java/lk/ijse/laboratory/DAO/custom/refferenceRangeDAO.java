package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.refferenceRanges;

import java.sql.SQLException;
import java.util.List;

public interface refferenceRangeDAO extends crudDAO<refferenceRanges> {
    List<refferenceRanges> getRangeList(String subId) throws SQLException, ClassNotFoundException;
    }
