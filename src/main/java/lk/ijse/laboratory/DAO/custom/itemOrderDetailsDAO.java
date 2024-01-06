package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.Dto.Tm.ordersTm;
import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.itemOrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface itemOrderDetailsDAO extends crudDAO<itemOrderDetail>{
    boolean saveItems(List<ordersTm> tmList, String orderId, Connection connection) throws SQLException, ClassNotFoundException;
}
