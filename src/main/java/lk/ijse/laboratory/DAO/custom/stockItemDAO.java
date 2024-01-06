package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.stockItem;

import java.sql.SQLException;
import java.util.List;

public interface stockItemDAO extends crudDAO<stockItem> {
    int warningStocks() throws SQLException, ClassNotFoundException;
    List<String> loadcategories() throws SQLException, ClassNotFoundException;

    }
