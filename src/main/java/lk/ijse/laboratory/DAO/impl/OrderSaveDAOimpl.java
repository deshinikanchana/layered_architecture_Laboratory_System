package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.custom.OrderSaveDAO;
import lk.ijse.laboratory.DAO.custom.itemOrderDetailsDAO;
import lk.ijse.laboratory.DAO.custom.ordersDAO;
import lk.ijse.laboratory.Dto.itemOrderDetailDto;
import lk.ijse.laboratory.Entity.attendance;
import lk.ijse.laboratory.Entity.itemOrderDetail;
import lk.ijse.laboratory.Entity.orders;
import lk.ijse.laboratory.db.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderSaveDAOimpl implements OrderSaveDAO {
    @Override
    public boolean saveOrder(itemOrderDetail entity) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection connection = null;
        try {
            itemOrderDetailsDAO IDao = new itemOrderDetailsDAOimpl();

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isItemsSaved = IDao.saveItems(entity.getTmList(), entity.getOrderId(),connection);

            if (isItemsSaved) {
                ordersDAO OrDao = new ordersDAOimpl();
                orders orEntity = new orders(entity.getOrderId(), entity.getSupplierId(),Date.valueOf(LocalDate.now()),"PENDING");
                boolean isAdded = OrDao.Save(orEntity);
                if(isAdded) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
        return result;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<itemOrderDetail> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Save(itemOrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public itemOrderDetail Search(String code, String nic) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Update(itemOrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
