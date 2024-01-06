package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;
import lk.ijse.laboratory.Dto.salaryDto;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    String generateNextSalaryId() throws SQLException, ClassNotFoundException;
    List<salaryDto> loadAllSalaries() throws SQLException, ClassNotFoundException;
    List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException;
    float calculateTotalHours(String id,int month) throws SQLException, ClassNotFoundException;
    boolean SaveSalary(salaryDto dto) throws SQLException, ClassNotFoundException;
    employeeDto SearchEmployees(String code, String nic) throws SQLException, ClassNotFoundException;
    designationDto SearchDesignation(String code, String nic) throws SQLException, ClassNotFoundException;
    int getCount(String empId, int mon) throws SQLException, ClassNotFoundException;

}
