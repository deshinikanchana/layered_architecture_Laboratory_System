package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.userDAO;
import lk.ijse.laboratory.Entity.user;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.laboratory.controller.LoginFormController.newDto;

@AllArgsConstructor
@Data
public class userDAOimpl implements userDAO {


    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT userId FROM user ORDER BY userId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitUserId(resultSet.getString(1));
        }
        return splitUserId(null);
    }

    public String splitUserId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("U");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "U00" + id;
            } else {
                id++;
                return "U0" + id;
            }
        }
        return "U001";
    }

    @Override
    public List<user> loadAll() throws SQLException, ClassNotFoundException {
        List<user> dtoList = new ArrayList<>();

        ResultSet res = SQLUtil.execute("SELECT * FROM user");
        while (res.next()) {
            dtoList.add(new user(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(user entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?, ?, ?)", entity.getUserId(), entity.getUserName(), entity.getUserType(), entity.getPassword(), entity.getEmail());
    }

    @Override
    public user getLastAdmin() throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM user WHERE userType = 'Admin' ORDER BY userId DESC LIMIT 1");
        res.next();
        return new user(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
    }

    @Override
    public user Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM user WHERE " + col + " = ?",value);
        res.next();
        return new user(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
    }

    @Override
    public user CheckCredential(user entity) throws SQLException, ClassNotFoundException {
                ResultSet res = SQLUtil.execute("SELECT * FROM user WHERE userName = ? AND password = ?", entity.getUserName(), entity.getPassword());

                if (res.next()) {
                    entity.setUserId(res.getString(1));
                    entity.setUserType(res.getString(3));
                    entity.setEmail(res.getString(5));
                    return entity;
                }
        return entity;
    }


    @Override
    public boolean Update(user entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET userName = ?, password = ? ,email =? WHERE userId = ?", entity.getUserName(), entity.getPassword(), entity.getEmail(),newDto.getUserId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
