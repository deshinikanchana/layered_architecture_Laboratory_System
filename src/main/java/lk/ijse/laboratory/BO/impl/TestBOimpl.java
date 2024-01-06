package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.TestsBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.machineDAO;
import lk.ijse.laboratory.DAO.custom.sectionDAO;
import lk.ijse.laboratory.DAO.custom.testDAO;
import lk.ijse.laboratory.Dto.machineDto;
import lk.ijse.laboratory.Dto.sectionDto;
import lk.ijse.laboratory.Dto.testDto;
import lk.ijse.laboratory.Entity.machine;
import lk.ijse.laboratory.Entity.section;
import lk.ijse.laboratory.Entity.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestBOimpl implements TestsBO {
    testDAO TDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);
    machineDAO MDao = (machineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MACHINE);
    sectionDAO SecDao = (sectionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SECTION);
    @Override
    public String generateNextTestId() throws SQLException, ClassNotFoundException {
        return TDao.generateNextId();
    }

    @Override
    public List<testDto> loadAllTests() throws SQLException, ClassNotFoundException {
        List<test> tList = TDao.loadAll();
        List<testDto> testList = new ArrayList<>();
        for(test tst:tList){
            testList.add(new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId()));
        }
        return testList;
    }

    @Override
    public List<machineDto> loadAllMachines() throws SQLException, ClassNotFoundException {
        List<machine> mList = MDao.loadAll();
        List<machineDto> machineList = new ArrayList<>();
        for(machine mc : mList){
            machineList.add(new machineDto(mc.getMachineId(),mc.getSecId(),mc.getMachineName(),mc.getStatus()));
        }
        return machineList;
    }

    @Override
    public List<sectionDto> loadAllSections() throws SQLException, ClassNotFoundException {
        List<section> sList = SecDao.loadAll();
        List<sectionDto> sectionList = new ArrayList<>();
        for(section sec:sList){
            sectionList.add(new sectionDto(sec.getSecId(),sec.getSecName(),sec.getConsultant()));
        }
        return sectionList;
    }

    @Override
    public sectionDto SearchSection(String code, String nic) throws SQLException, ClassNotFoundException {
        section sec = SecDao.Search(code,nic);
        return new sectionDto(sec.getSecId(),sec.getSecName(),sec.getConsultant());
    }

    @Override
    public machineDto SearchMachine(String code, String nic) throws SQLException, ClassNotFoundException {
        machine mc = MDao.Search(code,nic);
        return new machineDto(mc.getMachineId(),mc.getSecId(),mc.getMachineName(),mc.getStatus());
    }

    @Override
    public boolean SaveTest(testDto dto) throws SQLException, ClassNotFoundException {
        return TDao.Save(new test(dto.getTestId(),dto.getSecId(),dto.getTest(),dto.getEstimatedTime(),dto.getPrice(),dto.getSampleType(),dto.getMachineId()));
    }

    @Override
    public testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException {
        test tst = TDao.Search(code,nic);
        return new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId());
    }

    @Override
    public boolean UpdateTest(testDto dto) throws SQLException, ClassNotFoundException {
        return TDao.Update(new test(dto.getTestId(),dto.getSecId(),dto.getTest(),dto.getEstimatedTime(),dto.getPrice(),dto.getSampleType(),dto.getMachineId()));
    }

    @Override
    public boolean DeleteTest(String id) throws SQLException, ClassNotFoundException {
        return TDao.Delete(id);
    }
}
