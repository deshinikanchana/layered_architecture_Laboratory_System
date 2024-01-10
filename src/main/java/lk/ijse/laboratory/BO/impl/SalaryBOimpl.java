package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.SalaryBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.attendanceDAO;
import lk.ijse.laboratory.DAO.custom.designationDAO;
import lk.ijse.laboratory.DAO.custom.employeeDAO;
import lk.ijse.laboratory.DAO.custom.salaryDAO;
import lk.ijse.laboratory.DAO.impl.attendanceDAOimpl;
import lk.ijse.laboratory.DAO.impl.designationDAOimpl;
import lk.ijse.laboratory.DAO.impl.employeeDAOimpl;
import lk.ijse.laboratory.DAO.impl.salaryDAOimpl;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;
import lk.ijse.laboratory.Dto.salaryDto;
import lk.ijse.laboratory.Entity.designation;
import lk.ijse.laboratory.Entity.employee;
import lk.ijse.laboratory.Entity.salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOimpl implements SalaryBO {
    designationDAO Desdao = (designationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DESIGNATION);
    attendanceDAO AtDao = (attendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    employeeDAO EDao = (employeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    salaryDAO SalDao = (salaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    @Override
    public String generateNextSalaryId() throws SQLException, ClassNotFoundException {
        return SalDao.generateNextId();
    }

    @Override
    public List<salaryDto> loadAllSalaries() throws SQLException, ClassNotFoundException {
        List<salary> sList =  SalDao.loadAll();
        List<salaryDto> salList = new ArrayList<>();
        for(salary sal:sList){
            salList.add(new salaryDto(sal.getSalId(),sal.getEmpId(),sal.getAmount(),sal.getPaidDate()));
        }
        return salList;
    }

    @Override
    public List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException {
        List<employee> eList = EDao.loadAll();
        List<employeeDto> empList = new ArrayList<>();
        for(employee emp:eList){
            empList.add(new employeeDto(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo()));
        }
        return empList;
    }

    @Override
    public float calculateTotalHours(String id, int month) throws SQLException, ClassNotFoundException {
        return AtDao.calculateTotalHours(id,month);
    }

    @Override
    public boolean SaveSalary(salaryDto dto) throws SQLException, ClassNotFoundException {
        return SalDao.Save(new salary(dto.getSalId(),dto.getEmpId(),dto.getAmount(),dto.getPaidDate()));
    }

    @Override
    public employeeDto SearchEmployees(String column, String value) throws SQLException, ClassNotFoundException {
        employee emp = EDao.Search(column,value);
        return new employeeDto(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo());
    }

    @Override
    public designationDto SearchDesignation(String column, String value) throws SQLException, ClassNotFoundException {
        designation des = Desdao.Search(column,value);
        return new designationDto(des.getJboId(),des.getJobTitle(),des.getWorkingHoursPerMonth(),des.getBasicSalary(),des.getOtPaymentsPerHour());
    }

    @Override
    public int getCount(String empId, int mon) throws SQLException, ClassNotFoundException {
        return AtDao.getCount(empId,mon);
    }
}
