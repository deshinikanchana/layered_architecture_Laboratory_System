package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.ordersDAO;
import lk.ijse.laboratory.Dto.ordersDto;
import lk.ijse.laboratory.Entity.orders;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
@Data
public class ordersDAOimpl implements ordersDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    @Override
    public List<orders> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    private String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "O00" + id;
            } else {
                id++;
                return "O0" + id;
            }
        }
        return "O001";
    }

    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(orderId) FROM orders WHERE status = 'PENDING'");

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean Save(orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES(?,?,?,?)", entity.getOrderId(), entity.getSupId(), entity.getDate(), entity.getStatus());
    }

    @Override
    public orders Search(String code, String nic) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Update(orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
