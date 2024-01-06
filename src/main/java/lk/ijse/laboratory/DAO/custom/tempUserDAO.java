package lk.ijse.laboratory.DAO.custom;


import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.user;


import java.sql.*;
import java.util.List;

public interface tempUserDAO extends crudDAO<user>{
    boolean saveTempUser(user entity, Date dt, Time tm) throws SQLException, ClassNotFoundException;
    List<user> searchNewUsers(Date today, Time just) throws SQLException, ClassNotFoundException;
    boolean deleteTempUsers(user entity) throws SQLException, ClassNotFoundException;
}
