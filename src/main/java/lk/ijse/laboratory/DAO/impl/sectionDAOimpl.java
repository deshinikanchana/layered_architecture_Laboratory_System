package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.sectionDAO;
import lk.ijse.laboratory.Entity.section;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class sectionDAOimpl  implements sectionDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT secId FROM section ORDER BY secId DESC LIMIT 1");

        if (res.next()) {
            return splitSectionId(res.getString(1));
        }
        return splitSectionId(null);
    }

    private String splitSectionId(String currentSectionId) {
        if (currentSectionId != null) {
            String[] split = currentSectionId.split("S");
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
    public List<section> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM section");
        List<section> secList = new ArrayList<>();

        while (res.next()) {
            secList.add(new section(res.getString(1), res.getString(2), res.getString(3)));
        }
        return secList;
    }

    @Override
    public int getTestCount(String secId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(secId) FROM test WHERE secId = ?",secId);
        if (resultSet.next()) {
           return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean Save(section entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO section VALUES(?, ?, ?)", entity.getSecId(), entity.getSecName(), entity.getConsultant());
    }

    @Override
    public boolean Update(section entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE section SET consultantName = ? WHERE secId = ?", entity.getConsultant(), entity.getSecId());
    }

    @Override
    public boolean Delete(String secId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM section WHERE secId = ?",secId);
    }

    @Override
    public section Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute("SELECT * FROM section WHERE " + col + "  = ?", value);
        res.next();
        return new section(res.getString(1),res.getString(2),res.getString(3));
    }
}

