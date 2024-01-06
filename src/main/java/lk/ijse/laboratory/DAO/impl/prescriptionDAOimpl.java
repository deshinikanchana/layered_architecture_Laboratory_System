package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.prescriptionDAO;
import lk.ijse.laboratory.Dto.prescriptionDto;
import lk.ijse.laboratory.Entity.prescription;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class prescriptionDAOimpl implements prescriptionDAO {

    @Override
    public List<prescription> loadAll() throws SQLException, ClassNotFoundException {
        List<prescription> dtoList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM prescription");
        while (rst.next()) {
            dtoList.add(new prescription(rst.getString(1),rst.getString(2),rst.getString(3),rst.getFloat(4),rst.getFloat(5)));
        }
        return dtoList;
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(presId) FROM prescription WHERE duePayment > 0");

        if (resultSet.next()) {
           return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT presId FROM prescription ORDER BY presId DESC LIMIT 1");
        if (resultSet.next()) {
            return splitPtId(resultSet.getString(1));
        }
        return splitPtId(null);
    }

    public String splitPtId(String lastId) {
        if (lastId != null) {
            String[] split = lastId.split("r");
            int id = Integer.parseInt(split[1]);
            if (id < 9) {
                id++;
                return "Pr00" + id;
            } else {
                id++;
                return "Pr0" + id;
            }
        }
        return "Pr001";
    }

    @Override
    public prescription SearchPrescriptionById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM prescription WHERE ptId = ? ORDER BY presId DESC LIMIT 1",id);
        rst.next();
        return new prescription(rst.getString(1),rst.getString(2),rst.getString(3),rst.getFloat(4),rst.getFloat(5));
    }

    @Override
    public prescription Search(String code,String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM prescription WHERE presId = ?",id);
        rst.next();
        return new prescription(rst.getString(1),rst.getString(2),rst.getString(3),rst.getFloat(4),rst.getFloat(5));
    }

    @Override
    public boolean Update(prescription entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Save(prescription entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO prescription VALUES(?, ?, ?, ?, ?)", entity.getPresId(), entity.getPtId(), entity.getRefferedBy(), entity.getTotalAmount(), entity.getDuePayment());
    }
}
