package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.EmployeeBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.designationDAO;
import lk.ijse.laboratory.DAO.custom.employeeDAO;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;
import lk.ijse.laboratory.Entity.designation;
import lk.ijse.laboratory.Entity.employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOimpl implements EmployeeBO {
    employeeDAO dao = (employeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    designationDAO DesDao = (designationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DESIGNATION);
    @Override
    public String generateNextEmployeeId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException {
        List<employee> em = dao.loadAll();
        List<employeeDto> empList = new ArrayList<>();
        for(employee emp:em){
            empList.add(new employeeDto(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo()));
        }
        return empList;
    }

    @Override
    public List<designationDto> loadAllDesignations() throws SQLException, ClassNotFoundException {
        List<designation> ds = DesDao.loadAll();
        List<designationDto> desList = new ArrayList<>();
        for(designation DS:ds){
            desList.add(new designationDto(DS.getJboId(),DS.getJobTitle(),DS.getWorkingHoursPerMonth(),DS.getBasicSalary(),DS.getOtPaymentsPerHour()));
        }
        return desList;
    }

    @Override
    public boolean SaveEmployee(employeeDto emp) throws SQLException, ClassNotFoundException {
        return dao.Save(new employee(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo()));
    }

    @Override
    public employeeDto SearchEmployee(String column, String value) throws SQLException, ClassNotFoundException {
        employee emp = dao.Search(column,value);
        return new employeeDto(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo());
    }

    @Override
    public designationDto SearchDesignation(String column, String value) throws SQLException, ClassNotFoundException {
        designation DS = DesDao.Search(column,value);
        return new designationDto(DS.getJboId(),DS.getJobTitle(),DS.getWorkingHoursPerMonth(),DS.getBasicSalary(),DS.getOtPaymentsPerHour());
    }

    @Override
    public boolean UpdateEmployee(employeeDto emp) throws SQLException, ClassNotFoundException {
        return dao.Update(new employee(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo()));
    }

    @Override
    public boolean DeleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return dao.Delete(id);
    }
}
