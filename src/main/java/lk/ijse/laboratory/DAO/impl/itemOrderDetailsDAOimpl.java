package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.itemOrderDetailsDAO;
import lk.ijse.laboratory.Dto.Tm.ordersTm;
import lk.ijse.laboratory.Dto.itemOrderDetailDto;
import lk.ijse.laboratory.Entity.attendance;
import lk.ijse.laboratory.Entity.itemOrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class itemOrderDetailsDAOimpl implements itemOrderDetailsDAO {

    @Override
    public boolean saveItems(List<ordersTm> tmList, String orderId, Connection connection) throws SQLException, ClassNotFoundException {
        for (ordersTm Otm : tmList) {
            if (!saveOrderItems(Otm,orderId,connection)) {
                return false;
            }
        }
        return true;
    }
    private boolean saveOrderItems(ordersTm otm, String orderId, Connection connection) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item_order_details VALUES(?, ?, ?)",otm.getItemCode(),orderId,otm.getQty());
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
    public itemOrderDetail Search(String col, String value) throws SQLException, ClassNotFoundException {
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
