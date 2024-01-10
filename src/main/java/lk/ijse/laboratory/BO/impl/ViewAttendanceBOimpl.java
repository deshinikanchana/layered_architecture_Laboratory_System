package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.ViewAttendanceBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.attendanceDAO;
import lk.ijse.laboratory.DAO.custom.designationDAO;
import lk.ijse.laboratory.DAO.custom.employeeDAO;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;
import lk.ijse.laboratory.Entity.attendance;
import lk.ijse.laboratory.Entity.designation;
import lk.ijse.laboratory.Entity.employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceBOimpl implements ViewAttendanceBO {
    employeeDAO EmpDao = (employeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    attendanceDAO AtDao = (attendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    designationDAO DesDao = (designationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DESIGNATION);
    @Override
    public List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException {
        List<employee> eList = EmpDao.loadAll();
        List<employeeDto> empList = new ArrayList<>();
        for(employee emp:eList){
            empList.add(new employeeDto(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo()));
        }
        return empList;
    }

    @Override
    public List<attendanceDto> loadAllAttendances() throws SQLException, ClassNotFoundException {
        List<attendance> aList =  AtDao.loadAll();
        List<attendanceDto> attendanceList = new ArrayList<>();
        for(attendance at:aList){
            attendanceList.add(new attendanceDto(at.getEmpId(),at.getDate(),at.getInTime(),at.getOutTime(),at.getStatus()));
        }
        return attendanceList;
    }

    @Override
    public float calculateTotalHours(String id, int month) throws SQLException, ClassNotFoundException {
        return AtDao.calculateTotalHours(id,month);
    }

    @Override
    public int getCount(String empId, int mon) throws SQLException, ClassNotFoundException {
        return AtDao.getCount(empId,mon);
    }

    @Override
    public employeeDto SearchEmployee(String column, String value) throws SQLException, ClassNotFoundException {
        employee emp = EmpDao.Search(column,value);
        return new employeeDto(emp.getEmpId(),emp.getJobId(),emp.getUserId(),emp.getName(),emp.getNic(),emp.getAddress(),emp.getEmail(),emp.getTelNo());
    }

    @Override
    public attendanceDto SearchAttendances(String column, String value) throws SQLException, ClassNotFoundException {
        attendance at = AtDao.Search(column,value);
        return new attendanceDto(at.getEmpId(),at.getDate(),at.getInTime(),at.getOutTime(),at.getStatus());
    }

    @Override
    public designationDto SearchDesignations(String column, String value) throws SQLException, ClassNotFoundException {
        designation ds = DesDao.Search(column,value);
        return new designationDto(ds.getJboId(),ds.getJobTitle(),ds.getWorkingHoursPerMonth(),ds.getBasicSalary(),ds.getOtPaymentsPerHour());
    }
}
