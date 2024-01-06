package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.ReportsBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.*;
import lk.ijse.laboratory.Dto.*;
import lk.ijse.laboratory.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportBOimpl implements ReportsBO {
    reportDAO RepDao = (reportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REPORT);
    prescriptionDAO PrDao = (prescriptionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION);
    patientDAO PtDao = (patientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);
    testDAO TDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);
    collectingCenterDAO dao = (collectingCenterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COLLECTINGCENTER);
    machineDAO MDao = (machineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MACHINE);
    refferenceRangeDAO RefDao = (refferenceRangeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REFFERENCERANGES);
    resultDAO ResDao = (resultDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESULT);
    sectionDAO SecDao = (sectionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SECTION);
    subTestDAO STDao = (subTestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUBTEST);
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return RepDao.generateNextId();
    }

    @Override
    public List<ptTestDetailsDto> loadAllReports() throws SQLException, ClassNotFoundException {
        List<ptTestDetails> rList = RepDao.loadAll();
        List<ptTestDetailsDto> repList = new ArrayList<>();
        for(ptTestDetails pt:rList){
            repList.add(new ptTestDetailsDto(pt.getDate(),pt.getPresId(),pt.getTestId(),pt.getStatus(),pt.getComment()));
        }
        return repList;
    }

    @Override
    public List<prescriptionDto> loadAllPrescriptions() throws SQLException, ClassNotFoundException {
        List<prescription> pList = PrDao.loadAll();
        List<prescriptionDto> presList = new ArrayList<>();
        for(prescription pres:pList){
            presList.add(new prescriptionDto(pres.getPresId(),pres.getPtId(),pres.getRefferedBy(),pres.getTotalAmount(),pres.getDuePayment()));
        }
        return presList;
    }

    @Override
    public List<ptTestDetailsDto> loadTestIds(String Id) throws SQLException, ClassNotFoundException {
        List<ptTestDetails> ptList =  RepDao.loadTestIds(Id);
        List<ptTestDetailsDto> repList = new ArrayList<>();
        for(ptTestDetails pt:ptList){
            repList.add(new ptTestDetailsDto(pt.getDate(),pt.getPresId(),pt.getTestId(),pt.getStatus(),pt.getComment()));
        }
        return repList;
    }

    @Override
    public List<subTestDto> getAllsubTests(String text) throws SQLException, ClassNotFoundException {
        List<subTest> sList = STDao.getAllsubTests(text);
        List<subTestDto> subList = new ArrayList<>();
        for(subTest st:sList){
            subList.add(new subTestDto(st.getTestId(),st.getSubTestId(),st.getName()));
        }
        return subList;
    }

    @Override
    public List<refferenceRangesDto> getRangeList(String subId) throws SQLException, ClassNotFoundException {
        List<refferenceRanges> rList = RefDao.getRangeList(subId);
        List<refferenceRangesDto> refList = new ArrayList<>();
        for(refferenceRanges ref:rList){
            refList.add(new refferenceRangesDto(ref.getSubTestId(),ref.getRanges(),ref.getUnit()));
        }
        return refList;
    }

    @Override
    public prescriptionDto SearchPrescription(String code, String nic) throws SQLException, ClassNotFoundException {
        prescription pr = PrDao.Search(code,nic);
        return new prescriptionDto(pr.getPresId(),pr.getPtId(),pr.getRefferedBy(),pr.getTotalAmount(),pr.getDuePayment());
    }

    @Override
    public patientDto SearchPatients(String code, String nic) throws SQLException, ClassNotFoundException {
        patient pt = PtDao.Search(code,nic);
        return new patientDto(pt.getPtId(),pt.getUserId(),pt.getCcId(),pt.getName(),pt.getGender(),pt.getDob(),pt.getTelNo(),pt.getEmail());
    }

    @Override
    public testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException {
        test tst = TDao.Search(code,nic);
        return new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId());
    }

    @Override
    public machineDto SearchMachines(String code, String nic) throws SQLException, ClassNotFoundException {
        machine entity =  MDao.Search(code,nic);
        return new machineDto(entity.getMachineId(),entity.getSecId(),entity.getMachineName(),entity.getStatus());
    }

    @Override
    public sectionDto SearchSections(String code, String nic) throws SQLException, ClassNotFoundException {
        section entity = SecDao.Search(code,nic);
        return new sectionDto(entity.getSecId(),entity.getSecName(),entity.getConsultant());
    }

    @Override
    public collectingCenterDto SearchCenters(String code, String nic) throws SQLException, ClassNotFoundException {
        collectingCenter cc = dao.Search(code,nic);
        return new collectingCenterDto(cc.getCcId(),cc.getCenterName(),cc.getAddress(),cc.getTelNo(),cc.getEmail());
    }

    @Override
    public resultDto SearchResults(String code, String nic) throws SQLException, ClassNotFoundException {
        result re = ResDao.Search(code,nic);
        return new resultDto(re.getPresId(),re.getSubTestId(),re.getResult());
    }

    @Override
    public boolean UpdateReports(ptTestDetailsDto pt) throws SQLException, ClassNotFoundException {
        return RepDao.Update(new ptTestDetails(pt.getDate(),pt.getPresId(),pt.getTestId(),pt.getStatus(),pt.getComment()));
    }
}
