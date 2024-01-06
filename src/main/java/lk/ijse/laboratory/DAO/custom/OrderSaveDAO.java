package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.itemOrderDetail;

import java.sql.SQLException;

public interface OrderSaveDAO extends crudDAO<itemOrderDetail> {
    boolean saveOrder(itemOrderDetail entity) throws SQLException, ClassNotFoundException;
}
