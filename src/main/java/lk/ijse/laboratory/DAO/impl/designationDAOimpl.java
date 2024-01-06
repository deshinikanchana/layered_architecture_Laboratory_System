package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.designationDAO;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Entity.designation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class designationDAOimpl implements designationDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT jobId FROM job_role ORDER BY jobId DESC LIMIT 1");
        if (resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    private String splitId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("J");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "J00" + id;
            } else {
                id++;
                return "J0" + id;
            }
        }
        return "J001";
    }

    @Override
    public List<designation> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM job_role");
        List<designation> jobList = new ArrayList<>();
        while (rst.next()) {
            jobList.add(new designation(
                    rst.getString(1), rst.getString(2), rst.getInt(3), rst.getFloat(4),rst.getFloat(5)));
        }
        return jobList;
    }

    @Override
    public int getEmpCount(String jobId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT COUNT(jobId) FROM employee WHERE jobId = ?",jobId);

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean Save(designation entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO job_role VALUES(?, ?, ?, ?, ?)", entity.getJboId(), entity.getJobTitle(), entity.getWorkingHoursPerMonth(), entity.getBasicSalary(), entity.getOtPaymentsPerHour());
    }

    @Override
    public boolean Update(designation entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE job_role SET jobTitle = ?, workingHoursPerMonth = ? ,basicSalary = ?, otPaymentsPerHour = ? WHERE jobId = ?", entity.getJobTitle(), entity.getWorkingHoursPerMonth(), entity.getBasicSalary(), entity.getOtPaymentsPerHour(), entity.getJboId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM job_role WHERE jobId = ?",id);
    }

    @Override
    public designation Search(String code,String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM job_role WHERE jobId = ?",id);
        rst.next();
            return new designation(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getFloat(4),rst.getFloat(5));
    }
}
