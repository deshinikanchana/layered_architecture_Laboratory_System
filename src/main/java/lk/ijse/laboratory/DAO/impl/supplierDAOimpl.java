package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.supplierDAO;
import lk.ijse.laboratory.Entity.supplier;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class supplierDAOimpl implements supplierDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT supId FROM supplier ORDER BY supId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitSupId(resultSet.getString(1));
        }
        return splitSupId(null);
    }

    private String splitSupId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "S00" + id;
            } else {
                id++;
                return "S0" + id;
            }
        }
        return "S001";
    }

    @Override
    public List<supplier> loadAll() throws SQLException, ClassNotFoundException {
        List<supplier> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM supplier");

        while (res.next()) {
            dtoList.add(new supplier(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier VALUES(?, ?, ?, ?, ?)", entity.getSupId(), entity.getName(), entity.getTelNo(), entity.getEmail(), entity.getCategory());
    }

    @Override
    public boolean Update(supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET name = ?, telNo = ?, email = ?, category = ? WHERE supId = ?", entity.getName(), entity.getTelNo(), entity.getEmail(), entity.getCategory(), entity.getSupId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supId = ?",id);
    }

    @Override
    public supplier Search(String code,String Name) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM supplier WHERE " + code + " = ?",Name);
        res.next();
        return new supplier(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
    }
}
