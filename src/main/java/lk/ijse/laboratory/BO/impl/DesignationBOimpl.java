package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.DesignationBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.designationDAO;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Entity.designation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DesignationBOimpl implements DesignationBO {
    designationDAO dao = (designationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DESIGNATION);
    @Override
    public String generateNextDesignationId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<designationDto> loadAllDesignations() throws SQLException, ClassNotFoundException {
        List<designation> desList = dao.loadAll();
        List<designationDto> DesignationList = new ArrayList<>();
        for(designation ds : desList){
            DesignationList.add(new designationDto(ds.getJboId(),ds.getJobTitle(),ds.getWorkingHoursPerMonth(),ds.getBasicSalary(),ds.getOtPaymentsPerHour()));
        }
        return DesignationList;
    }

    @Override
    public int getEmpCount(String jobId) throws SQLException, ClassNotFoundException {
        return dao.getEmpCount(jobId);
    }

    @Override
    public boolean SaveDesignation(designationDto dto) throws SQLException, ClassNotFoundException {
        return dao.Save(new designation(dto.getJboId(),dto.getJobTitle(),dto.getWorkingHoursPerMonth(),dto.getBasicSalary(),dto.getOtPaymentsPerHour()));
    }

    @Override
    public designationDto SearchDesignation(String code, String nic) throws SQLException, ClassNotFoundException {
        designation ds = dao.Search(code,nic);
        return new designationDto(ds.getJboId(),ds.getJobTitle(),ds.getWorkingHoursPerMonth(),ds.getBasicSalary(),ds.getOtPaymentsPerHour());
    }

    @Override
    public boolean UpdateDesignation(designationDto dto) throws SQLException, ClassNotFoundException {
        return dao.Update(new designation(dto.getJboId(),dto.getJobTitle(),dto.getWorkingHoursPerMonth(),dto.getBasicSalary(),dto.getOtPaymentsPerHour()));
    }

    @Override
    public boolean DeleteDesignation(String id) throws SQLException, ClassNotFoundException {
        return dao.Delete(id);
    }
}
