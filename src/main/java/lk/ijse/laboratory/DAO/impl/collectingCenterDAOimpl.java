package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.collectingCenterDAO;
import lk.ijse.laboratory.Entity.collectingCenter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class collectingCenterDAOimpl implements collectingCenterDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT ccId FROM collecting_center ORDER BY ccId DESC LIMIT 1");

        if (resultSet.next()) {
            return splitCCId(resultSet.getString(1));
        }
        return splitCCId(null);
    }

    private String splitCCId(String currentCCId) {
        if (currentCCId != null) {
            String[] split = currentCCId.split("C");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "C00" + id;
            } else {
                id++;
                return "C0" + id;
            }
        }
        return "C001";
    }

    @Override
    public List<collectingCenter> loadAll() throws SQLException, ClassNotFoundException {
        List<collectingCenter> dtoList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM collecting_center");

        while (rst.next()) {
            dtoList.add(new collectingCenter(rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5)));
        }
        return dtoList;
    }

    @Override
    public int getSampleCount(String CcId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(ccId) FROM patient WHERE ccId = ?",CcId);

        if (resultSet.next()) {
          return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean Save(collectingCenter entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO collecting_center VALUES(?, ?, ?, ?, ?)",entity.getCcId(),entity.getCenterName(),entity.getAddress(),entity.getTelNo(),entity.getEmail());
    }

    @Override
    public boolean Update(collectingCenter entity) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("UPDATE collecting_center SET address = ?,telNo = ?,email = ? WHERE ccId = ?", entity.getAddress(), entity.getTelNo(), entity.getEmail(), entity.getCcId());
    }

    @Override
    public boolean Delete(String ccId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM collecting_center WHERE ccId = ?",ccId);
    }

    @Override
    public collectingCenter Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet rst =SQLUtil.execute("SELECT * FROM collecting_center WHERE ccId = ?", value);
        rst.next();
        return new collectingCenter(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));
    }
}
