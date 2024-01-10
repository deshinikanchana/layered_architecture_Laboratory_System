package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.employeeDAO;
import lk.ijse.laboratory.Entity.employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class employeeDAOimpl implements employeeDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private String splitEmployeeId(String currentEmployeeId) {
        if (currentEmployeeId != null) {
            String[] split = currentEmployeeId.split("E");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "E00" + id;
            } else {
                id++;
                return "E0" + id;
            }
        }
        return "E001";
    }

    @Override
    public List<employee> loadAll() throws SQLException, ClassNotFoundException {
        List<employee> dtoList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");

        while (rst.next()) {
            dtoList.add(new employee(rst.getString(1), rst.getString(2),rst.getString(3), rst.getString(4), rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?)",entity.getEmpId(),entity.getJobId(),entity.getUserId(),entity.getName(),entity.getNic(),entity.getAddress(),entity.getEmail(),entity.getTelNo());
    }

    @Override
    public employee Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE " + col + "  = ?", value);
        resultSet.next();
           return new employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8));
    }

    @Override
    public boolean Update(employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET jobId = ?, address = ?, email = ?, telNo = ? WHERE empId = ?",entity.getJobId(),entity.getAddress(),entity.getEmail(),entity.getTelNo(),entity.getEmpId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Delete from employee where empId = ?",id);
    }
}
