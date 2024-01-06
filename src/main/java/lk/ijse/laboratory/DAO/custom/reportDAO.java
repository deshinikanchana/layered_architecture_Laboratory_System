package lk.ijse.laboratory.DAO.custom;
import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.ptTestDetails;

import java.sql.*;
import java.util.List;

public interface reportDAO extends crudDAO<ptTestDetails> {
   int getCount() throws SQLException, ClassNotFoundException;
   int getPendingReports() throws SQLException, ClassNotFoundException;
   List<ptTestDetails> loadTestIds(String Id) throws SQLException, ClassNotFoundException;

    }
