package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.orders;

import java.sql.SQLException;

public interface ordersDAO extends crudDAO<orders> {
    int getOrderCount() throws SQLException, ClassNotFoundException;
}
