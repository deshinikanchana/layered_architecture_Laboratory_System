package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.testDAO;
import lk.ijse.laboratory.Entity.test;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data
public class testDAOimpl implements testDAO {


    @Override
    public test Search(String code, String id) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM test WHERE " + code +" = ?",id);
        res.next();
        return new test(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getFloat(5),res.getString(6),res.getString(7));
    }

    @Override
    public List<test> loadAll() throws SQLException, ClassNotFoundException {
        List<test> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM test");

        while (res.next()) {
            dtoList.add(new test(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getFloat(5),res.getString(6),res.getString(7)));
        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT testId FROM test ORDER BY testId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

   private String splitId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("T");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "T00" + id;
            } else {
                id++;
                return "T0" + id;
            }
        }
        return "T001";
    }


    @Override
    public boolean Save(test entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO test VALUES(?, ?, ?, ?, ?, ?, ?)", entity.getTestId(), entity.getSecId(), entity.getTest(), entity.getEstimatedTime(), entity.getPrice(), entity.getSampleType(), entity.getMachineId());
    }

    @Override
    public boolean Update(test entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE test SET estimatedTime = ?, testPrice = ?  WHERE testId = ?", entity.getEstimatedTime(), entity.getPrice(), entity.getTestId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM test WHERE testId = ?",id);
    }
}
