package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.subTestDAO;
import lk.ijse.laboratory.Entity.subTest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class subTestDAOimpl implements subTestDAO {
    @Override
    public List<subTest> getAllsubTests(String text) throws SQLException, ClassNotFoundException {
        List<subTest> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM sub_test Where testId = ?",text);

        while (res.next()) {
            dtoList.add(new subTest(res.getString(1),res.getString(2),res.getString(3)));
        }
        return dtoList;
    }

    @Override
    public subTest Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM sub_test Where subTestId = ?", value);
        res.next();
        return new subTest(res.getString(1),res.getString(2),res.getString(3));
    }

    @Override
    public boolean Update(subTest entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT subTestId FROM sub_test ORDER BY subTestId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public List<subTest> loadAll() throws SQLException, ClassNotFoundException {
        List<subTest> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM sub_test ORDER BY subTestId DESC");

        while (res.next()) {
            dtoList.add(new subTest(res.getString(1),res.getString(2),res.getString(3)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(subTest entity) throws SQLException, ClassNotFoundException {
        return false;
    }


    private String splitId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("T");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "ST000" + id;
            } else if (id < 100) {
                id++;
                return "ST00" + id;
            } else if (id < 1000) {
                id++;
                return "ST0" + id;
            } else {
                id++;
                return "ST" + id;
            }
        }
        return "ST0001";
    }
}