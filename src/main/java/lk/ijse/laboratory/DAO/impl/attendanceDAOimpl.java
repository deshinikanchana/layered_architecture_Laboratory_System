package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.attendanceDAO;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Entity.attendance;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data

public class attendanceDAOimpl implements attendanceDAO {
    @Override
    public float calculateTotalHours(String id,int month) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT sum(TIMEDIFF(outTime,inTime)) FROM attendance WHERE month(date) = ?  AND  empId = ?",month,id);
        float hours =0;

        if (resultSet.next()) {
             hours = resultSet.getInt(1);
        }
        return (hours/10000);
    }

    @Override
    public int getCount(String empId, int mon) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) FROM attendance WHERE month(date) = ? AND  empId = ?",mon,empId);

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    @Override
    public int getDailyCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) FROM attendance WHERE date = ?",java.sql.Date.valueOf(LocalDate.now()));
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<attendance> loadAll() throws SQLException, ClassNotFoundException {
        List<attendance> dtoList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute( "SELECT * FROM attendance");

        while (rst.next()) {
            dtoList.add(new attendance(rst.getString(1),rst.getDate(2),rst.getTime(3),rst.getTime(4),rst.getString(5)));
        }
        return dtoList;
    }

    @Override
    public attendance Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM attendance WHERE empId = ?", value);
        rst.next();
        return new attendance(rst.getString(1),rst.getDate(2),rst.getTime(3),rst.getTime(4),rst.getString(5));
    }

    @Override
    public boolean Save(attendance entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO attendance VALUES(?, ?, ?, ?, ?)",entity.getEmpId(),(java.sql.Date) entity.getDate(),entity.getInTime(),entity.getOutTime(),entity.getStatus());
    }

    @Override
    public attendanceDto searchAttendancesById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM attendance WHERE empId = ? AND date = ?", id, java.sql.Date.valueOf(LocalDate.now()));
        attendanceDto DTO = new attendanceDto();
        while (rst.next()) {
           DTO = new attendanceDto(rst.getString(1), rst.getDate(2), rst.getTime(3), rst.getTime(4), rst.getString(5));
        }
        return DTO;
    }


    @Override
    public boolean Update(attendance entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE attendance SET outTime = ?,status = ?  WHERE empId = ?",entity.getOutTime(),entity.getStatus(),entity.getEmpId());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
