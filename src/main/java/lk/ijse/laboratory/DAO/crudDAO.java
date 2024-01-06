package lk.ijse.laboratory.DAO;

import lk.ijse.laboratory.Entity.attendance;

import java.sql.SQLException;
import java.util.List;

public interface crudDAO<T> extends SuperDAO {
    String generateNextId() throws SQLException, ClassNotFoundException;
    List<T> loadAll() throws SQLException, ClassNotFoundException;
    boolean Save(T dto) throws SQLException, ClassNotFoundException;
    T Search(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean Update(T dto) throws SQLException, ClassNotFoundException;
    boolean Delete(String id) throws SQLException, ClassNotFoundException;
}
