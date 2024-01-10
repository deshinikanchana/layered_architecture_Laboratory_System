package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.tempUserDAO;
import lk.ijse.laboratory.Entity.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class tempUserDAOimpl implements tempUserDAO {
    @Override
    public boolean saveTempUser(user entity, Date dt, Time tm) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "INSERT INTO user_temp VALUES(?, ?, ?, ?, ?,?,?)", entity.getUserId(), entity.getUserName(), entity.getUserType(), entity.getPassword(), entity.getEmail(),dt,tm);
    }
    @Override
    public List<user> searchNewUsers(Date today, Time just) throws SQLException, ClassNotFoundException {
        List<user> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM user_temp where date < ? and time <= ?",today,just);

        while (res.next()) {
            dtoList.add(new user(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
        }
        return dtoList;
    }

    @Override
    public boolean deleteTempUsers(user entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Delete from user_temp where userId = ?", entity.getUserId());
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<user> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Save(user entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public user Search(String col, String value) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Update(user entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
