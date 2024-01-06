package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.instructionDAO;
import lk.ijse.laboratory.Dto.instructionDto;
import lk.ijse.laboratory.Entity.instruction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class instructionDAOimpl implements instructionDAO {

    @Override
    public ArrayList<String> getInstructions(String id) throws SQLException, ClassNotFoundException {
        ArrayList<String> ins = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM instruction where testId = ?",id);
        while (resultSet.next()) {
          ins.add(resultSet.getString(3));
        }
        return ins;
    }

    @Override
    public List<instruction> loadAll() throws SQLException, ClassNotFoundException {
        List<instruction> dtoList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM instruction");
        while (resultSet.next()) {
            dtoList.add(new instruction(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3)));
        }
        return dtoList;
    }

   @Override
   public boolean Save(instruction entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO instruction VALUES(?,?,?)", entity.getNo(), entity.getTestId(), entity.getInstrucion());
    }

    @Override
    public instruction Search(String code, String nic) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
   public boolean Update(instruction entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE instruction SET instructions = ? WHERE no = ?", entity.getInstrucion(), entity.getNo());
    }

   @Override
   public boolean Delete(String no) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM instruction WHERE no = ?",Integer.parseInt(no));
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
            ResultSet resultSet = SQLUtil.execute("SELECT no FROM instruction ORDER BY no DESC LIMIT 1");
            if (resultSet.next()) {
                return String.valueOf((resultSet.getInt(1)+1));
            }
            return "1";
        }
}
