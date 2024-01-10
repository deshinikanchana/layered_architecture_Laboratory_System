package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Dto.employeeDto;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceBO extends SuperBO {
    List<attendanceDto> loadAllAttendance() throws SQLException, ClassNotFoundException;
    List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException;
    boolean SaveAttendance(attendanceDto dto) throws SQLException, ClassNotFoundException;
    attendanceDto searchAttendancesById(String id) throws SQLException, ClassNotFoundException;
    employeeDto SearchEmployee(String column, String value) throws SQLException, ClassNotFoundException;
    boolean Update(attendanceDto dto) throws SQLException, ClassNotFoundException;
}
