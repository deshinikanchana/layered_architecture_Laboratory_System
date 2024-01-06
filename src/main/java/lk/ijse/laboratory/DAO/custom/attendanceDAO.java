package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Entity.attendance;

import java.sql.*;

public interface attendanceDAO extends crudDAO<attendance> {
    float calculateTotalHours(String id,int month) throws SQLException, ClassNotFoundException;
    int getCount(String empId, int mon) throws SQLException, ClassNotFoundException;
    int getDailyCount() throws SQLException, ClassNotFoundException;
    attendanceDto searchAttendancesById(String id) throws SQLException, ClassNotFoundException;
}
