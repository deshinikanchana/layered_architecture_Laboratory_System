package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.salaryDAO;
import lk.ijse.laboratory.Dto.salaryDto;
import lk.ijse.laboratory.Entity.salary;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class salaryDAOimpl implements salaryDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT salId FROM salary ORDER BY salId DESC LIMIT 1");
        if (resultSet.next()) {
            return splitSalId(resultSet.getString(1));
        }
        return splitSalId(null);
    }

    private String splitSalId(String currentSalId) {
        if (currentSalId != null) {
            String[] split = currentSalId.split("S");
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
    public List<salary> loadAll() throws SQLException, ClassNotFoundException {
        List<salary> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM salary");

        while (res.next()) {
            dtoList.add(new salary(res.getString(1),res.getString(2),res.getDouble(3),res.getDate(4)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(salary entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO salary VALUES(?, ?, ?, ?)", entity.getSalId(), entity.getEmpId(), entity.getAmount(),(Date) entity.getPaidDate());
    }

    @Override
    public salary Search(String code, String nic) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Update(salary entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
