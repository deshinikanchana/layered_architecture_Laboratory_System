package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.patientDAO;
import lk.ijse.laboratory.Entity.patient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class patientDAOimpl implements patientDAO {
    @Override
    public boolean Save(patient entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO patient VALUES(?, ?, ?, ?, ?, ?, ?, ?)", entity.getPtId(), entity.getUserId(), entity.getCcId(), entity.getName(), entity.getGender(), entity.getDob(), entity.getTelNo(), entity.getEmail());
    }


    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT ptId FROM patient ORDER BY ptId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitPtId(resultSet.getString(1));
        }
        return splitPtId(null);
    }

    public String splitPtId(String currentPtId) {
        int id;
        if (currentPtId != null) {
            String[] split = currentPtId.split("P");
            id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "P00" + id;
            } else {
                id++;
                return "P0" + id;
            }
        }
        return "P001";
    }


    @Override
    public List<patient> loadAll() throws SQLException, ClassNotFoundException {
        List<patient> dtoList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM patient");

        while (rst.next()) {
            dtoList.add(new patient(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8)));
        }
        return dtoList;
    }

    @Override
    public boolean Update(patient entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE patient SET name = ?, DOB = ?, telNo = ?, email = ? WHERE ptId = ?", entity.getName(), entity.getDob(), entity.getTelNo(), entity.getEmail(), entity.getPtId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM patient WHERE ptId = ?",id);
    }

    @Override
    public patient Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM patient WHERE ptId = ?", value);
        rst.next();
        return new patient(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8));
    }
}
