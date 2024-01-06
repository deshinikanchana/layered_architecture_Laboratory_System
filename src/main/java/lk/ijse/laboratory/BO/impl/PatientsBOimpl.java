package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.PatientsBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.collectingCenterDAO;
import lk.ijse.laboratory.DAO.custom.patientDAO;
import lk.ijse.laboratory.DAO.impl.collectingCenterDAOimpl;
import lk.ijse.laboratory.DAO.impl.patientDAOimpl;
import lk.ijse.laboratory.Dto.collectingCenterDto;
import lk.ijse.laboratory.Dto.patientDto;
import lk.ijse.laboratory.Entity.collectingCenter;
import lk.ijse.laboratory.Entity.patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientsBOimpl implements PatientsBO {

    patientDAO ptDao = (patientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);
    collectingCenterDAO ccDao = (collectingCenterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COLLECTINGCENTER);

    @Override
    public String generateNextPatientId() throws SQLException, ClassNotFoundException {
        return ptDao.generateNextId();
    }

    @Override
    public List<patientDto> loadAllPatients() throws SQLException, ClassNotFoundException {
        List<patient> pList = ptDao.loadAll();
        List<patientDto> ptList = new ArrayList<>();
        for(patient pt : pList){
            ptList.add(new patientDto(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail()));
        }
        return ptList;
    }

    @Override
    public List<collectingCenterDto> loadAllCenters() throws SQLException, ClassNotFoundException {
        List<collectingCenter> cList = ccDao.loadAll();
        List<collectingCenterDto> centerList = new ArrayList<>();
        for(collectingCenter cl:cList){
            centerList.add(new collectingCenterDto(cl.getCcId(),cl.getCenterName(),cl.getAddress(),cl.getTelNo(),cl.getEmail()));
        }
        return centerList;
    }

    @Override
    public boolean SavePatient(patientDto pt) throws SQLException, ClassNotFoundException {
        return ptDao.Save(new patient(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail()));
    }

    @Override
    public patientDto SearchPatient(String code, String nic) throws SQLException, ClassNotFoundException {
        patient pt = ptDao.Search(code,nic);
        return new patientDto(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail());
    }

    @Override
    public boolean UpdatePatient(patientDto pt) throws SQLException, ClassNotFoundException {
        return ptDao.Update(new patient(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail()));
    }

    @Override
    public boolean DeletePatient(String id) throws SQLException, ClassNotFoundException {
        return ptDao.Delete(id);
    }
}
