package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    String generateNextEmployeeId() throws SQLException, ClassNotFoundException;
    List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException;
    List<designationDto> loadAllDesignations() throws SQLException, ClassNotFoundException;
    boolean SaveEmployee(employeeDto dto) throws SQLException, ClassNotFoundException;
    employeeDto SearchEmployee(String code, String nic) throws SQLException, ClassNotFoundException;
    designationDto SearchDesignation(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateEmployee(employeeDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteEmployee(String id) throws SQLException, ClassNotFoundException;
}
