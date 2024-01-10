package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.refferenceRangeDAO;
import lk.ijse.laboratory.Dto.refferenceRangesDto;
import lk.ijse.laboratory.Entity.refferenceRanges;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class refferenceRangeDAOimpl implements refferenceRangeDAO {
    @Override
    public List<refferenceRanges> getRangeList(String subId) throws SQLException, ClassNotFoundException {
        List<refferenceRanges> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM refference_range Where subTestId = ?",subId);

        while (res.next()) {
            dtoList.add(new refferenceRanges(res.getString(1),res.getString(2),res.getString(3)));
        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<refferenceRanges> loadAll() throws SQLException, ClassNotFoundException {
        List<refferenceRanges> dtoList = new ArrayList<>();
        ResultSet res =  SQLUtil.execute("SELECT * FROM refference_range");

        while (res.next()) {
            dtoList.add(new refferenceRanges(res.getString(1),res.getString(2),res.getString(3)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(refferenceRanges entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO refference_range VALUES(?, ?, ?)", entity.getSubTestId(), entity.getRanges(), entity.getUnit());
    }

    @Override
    public refferenceRanges Search(String col, String value) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Update(refferenceRanges entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE refference_range SET ranges = ?, unit = ? WHERE subTestId = ?", entity.getRanges(), entity.getUnit(), entity.getSubTestId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM refference_range WHERE subTestId = ?",id);
    }
}
