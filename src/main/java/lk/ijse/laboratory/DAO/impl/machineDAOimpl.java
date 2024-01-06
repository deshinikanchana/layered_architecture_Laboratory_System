package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.machineDAO;
import lk.ijse.laboratory.Dto.machineDto;
import lk.ijse.laboratory.Entity.machine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class machineDAOimpl implements machineDAO {


    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<machine> loadAll() throws SQLException, ClassNotFoundException {
        List<machine> dtoList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM machine");

        while (rst.next()) {
            dtoList.add(new machine(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return dtoList;
    }

    @Override
    public boolean Save(machine entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO machine VALUES(?, ?, ?, ?)", entity.getMachineId(), entity.getMachineName(), entity.getSecId(), entity.getStatus());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM machine WHERE machineId = ?",id);
    }

    @Override
    public boolean Update(machine entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE machine SET status = ? WHERE machineId = ?", entity.getStatus(), entity.getMachineId());
    }

    @Override
    public machine Search(String code, String mcName) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM machine WHERE " + code + " = ?",mcName);
        rst.next();
        return new machine(rst.getString(1), rst.getString(2),rst.getString(3), rst.getString(4) );

    }
}

