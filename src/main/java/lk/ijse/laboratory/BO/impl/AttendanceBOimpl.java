package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.AttendanceBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.attendanceDAO;
import lk.ijse.laboratory.DAO.custom.employeeDAO;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Dto.employeeDto;
import lk.ijse.laboratory.Entity.attendance;
import lk.ijse.laboratory.Entity.employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOimpl implements AttendanceBO {
    attendanceDAO atDao = (attendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    employeeDAO eDao = (employeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public List<attendanceDto> loadAllAttendance() throws SQLException, ClassNotFoundException {
        List<attendance> Att=atDao.loadAll();

        ArrayList<attendanceDto> Attend=new ArrayList<>();
        for (attendance att:Att) {
            Attend.add(new attendanceDto(att.getEmpId(),att.getDate(),att.getInTime(),att.getOutTime(),att.getStatus()));
        }
        return Attend;
    }

    @Override
    public List<employeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException {
       List<employee> emp = eDao.loadAll();

        ArrayList<employeeDto> employees=new ArrayList<>();
        for (employee em:emp) {
            employees.add(new employeeDto(em.getEmpId(),em.getJobId(),em.getUserId(),em.getName(),em.getNic(),em.getAddress(),em.getEmail(),em.getTelNo()));
        }
        return employees;
    }

    @Override
    public boolean SaveAttendance(attendanceDto dto) throws SQLException, ClassNotFoundException {
        return atDao.Save(new attendance(dto.getEmpId(),dto.getDate(),dto.getInTime(),dto.getOutTime(),dto.getStatus()));
    }

    @Override
    public attendanceDto searchAttendancesById(String id) throws SQLException, ClassNotFoundException {
        return atDao.searchAttendancesById(id);
    }

    @Override
    public employeeDto SearchEmployee(String code, String nic) throws SQLException, ClassNotFoundException {
        employee em =eDao.Search(code,nic);
        return new employeeDto(em.getEmpId(),em.getJobId(),em.getUserId(),em.getName(),em.getNic(),em.getAddress(),em.getEmail(),em.getTelNo());
    }

    @Override
    public boolean Update(attendanceDto dto) throws SQLException, ClassNotFoundException {
        return atDao.Update(new attendance(dto.getEmpId(),dto.getDate(),dto.getInTime(),dto.getOutTime(),dto.getStatus()));
    }
}
