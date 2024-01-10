package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.resultDAO;
import lk.ijse.laboratory.Entity.result;

import java.sql.*;
import java.util.List;

public class resultDAOimpl implements resultDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<result> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Save(result entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO result VALUES(?, ?, ?)", entity.getPresId(), entity.getSubTestId(), entity.getResult());
    }

    @Override
    public result Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM result WHERE subTestId = ? AND presId = ?", col, value);
        res.next();
        return new result(res.getString(1),res.getString(2),res.getString(3));
    }

    @Override
    public boolean Update(result entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
