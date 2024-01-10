package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.reportDAO;
import lk.ijse.laboratory.Entity.ptTestDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class reportDAOimpl implements reportDAO {

    @Override
    public boolean Save(ptTestDetails entity) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute("INSERT INTO patient_test_details VALUES(?, ?, ?, ?, ?)", entity.getDate(), entity.getPresId(), entity.getTestId(), entity.getStatus(), entity.getComment());
    }

    @Override
    public ptTestDetails Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM patient_test_details WHERE presId = ? AND testId = ? ", col, value);
        res.next();
        return new ptTestDetails(res.getDate(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT COUNT(testId) FROM patient_test_details WHERE date = ?", java.sql.Date.valueOf(LocalDate.now()));
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int getPendingReports() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT count(presId) FROM patient_test_details WHERE status = 'report Not Ready'");
        if (resultSet.next()) {
           return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ptTestDetails> loadAll() throws SQLException, ClassNotFoundException {
        List<ptTestDetails> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM patient_test_details");

        while (res.next()) {
            dtoList.add(new ptTestDetails(res.getDate(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
        }
        return dtoList;
    }

    @Override
    public List<ptTestDetails> loadTestIds(String Id) throws SQLException, ClassNotFoundException {
        List<ptTestDetails> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM patient_test_details WHERE presId = ?",Id);

        while (res.next()) {
            dtoList.add(new ptTestDetails(res.getDate(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
        }
        return dtoList;
    }

    @Override
    public boolean Update(ptTestDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE patient_test_details SET status = ?, comment = ?  WHERE presId = ? AND testId = ?", entity.getStatus(), entity.getComment(), entity.getPresId(), entity.getTestId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
