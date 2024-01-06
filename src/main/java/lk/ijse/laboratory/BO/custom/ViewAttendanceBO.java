package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;

import java.sql.SQLException;
import java.util.List;

public interface ViewAttendanceBO extends SuperBO {
    List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException;
    List<attendanceDto> loadAllAttendances() throws SQLException, ClassNotFoundException;
    float calculateTotalHours(String id,int month) throws SQLException, ClassNotFoundException;
    int getCount(String empId, int mon) throws SQLException, ClassNotFoundException;
    employeeDto SearchEmployee(String code, String nic) throws SQLException, ClassNotFoundException;
    attendanceDto SearchAttendances(String code, String nic) throws SQLException, ClassNotFoundException;
    designationDto SearchDesignations(String code, String nic) throws SQLException, ClassNotFoundException;

}
