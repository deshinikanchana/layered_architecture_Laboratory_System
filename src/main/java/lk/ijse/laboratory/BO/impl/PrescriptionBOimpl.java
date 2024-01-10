package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.PrescriptionsBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.*;
import lk.ijse.laboratory.DAO.impl.*;
import lk.ijse.laboratory.Dto.*;
import lk.ijse.laboratory.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionBOimpl implements PrescriptionsBO {
    prescriptionDAO prDao = (prescriptionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION);
    patientDAO pttDao = (patientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);
    reportDAO repDao = (reportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REPORT);
    testDAO tDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);
    collectingCenterDAO ccDao = (collectingCenterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COLLECTINGCENTER);
    stockUsageDAO stDao = (stockUsageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKUSAGE);
    @Override
    public String generateNextPrescriptionId() throws SQLException, ClassNotFoundException {
        return prDao.generateNextId();
    }

    @Override
    public List<patientDto> loadAllPatients() throws SQLException, ClassNotFoundException {
        List<patient> pList = pttDao.loadAll();
        List<patientDto> ptList = new ArrayList<>();
        for(patient pt : pList){
            ptList.add(new patientDto(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail()));
        }
        return ptList;
    }

    @Override
    public List<testDto> loadAllTests() throws SQLException, ClassNotFoundException {
        List<test> tList = tDao.loadAll();
        List<testDto> testList = new ArrayList<>();
        for(test tst:tList){
            testList.add(new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId()));
        }
        return testList;
    }

    @Override
    public boolean SavePrescription(prescriptionDto dto) throws SQLException, ClassNotFoundException {
        return prDao.Save(new prescription(dto.getPresId(),dto.getPtId(),dto.getRefferedBy(),dto.getTotalAmount(),dto.getDuePayment()));
    }

    @Override
    public boolean SaveReport(ptTestDetailsDto dto) throws SQLException, ClassNotFoundException {
        return repDao.Save(new ptTestDetails(dto.getDate(),dto.getPresId(),dto.getTestId(),dto.getStatus(),dto.getComment()));
    }

    @Override
    public patientDto SearchPatient(String column, String value) throws SQLException, ClassNotFoundException {
        patient pt = pttDao.Search(column,value);
        return new patientDto(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail());
    }

    @Override
    public collectingCenterDto SearchCenter(String column, String value) throws SQLException, ClassNotFoundException {
        collectingCenter cc = ccDao.Search(column,value);
        return new collectingCenterDto(cc.getCcId(),cc.getCenterName(),cc.getAddress(),cc.getTelNo(),cc.getEmail());
    }

    @Override
    public testDto SearchTest(String column, String value) throws SQLException, ClassNotFoundException {
        test tst = tDao.Search(column,value);
        return new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId());
    }

    @Override
    public boolean UpdateStock(stockUsageDto dto) throws SQLException, ClassNotFoundException {
        return stDao.Update(new stockUsage(dto.getTestId(),dto.getItemCode(),dto.getQtyPerTest()));
    }

    @Override
    public prescriptionDto SearchPrescriptionById(String id) throws SQLException, ClassNotFoundException {
        prescription pr = prDao.SearchPrescriptionById(id);
        return new prescriptionDto(pr.getPresId(),pr.getPtId(),pr.getRefferedBy(),pr.getTotalAmount(),pr.getDuePayment());
    }

    @Override
    public List<stockUsageDto> getUsages(String testId) throws SQLException, ClassNotFoundException {
        List<stockUsage> stList = stDao.getUsages(testId);
        List<stockUsageDto> stockList = new ArrayList<>();
        for(stockUsage st:stList){
            stockList.add(new stockUsageDto(st.getTestId(),st.getItemCode(),st.getQtyPerTest()));
        }
        return stockList;
    }
}
