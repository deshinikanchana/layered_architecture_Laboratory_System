package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.TestDetailsBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.instructionDAO;
import lk.ijse.laboratory.DAO.custom.sectionDAO;
import lk.ijse.laboratory.DAO.custom.testDAO;
import lk.ijse.laboratory.Dto.sectionDto;
import lk.ijse.laboratory.Dto.testDto;
import lk.ijse.laboratory.Entity.section;
import lk.ijse.laboratory.Entity.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDetailsBOimpl implements TestDetailsBO {
    testDAO TDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);
    sectionDAO SecDao = (sectionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SECTION);
    instructionDAO InsDao = (instructionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INSTRUCTION);

    @Override
    public List<testDto> loadAllTest() throws SQLException, ClassNotFoundException {
        List<test> tList = TDao.loadAll();
        List<testDto> testList = new ArrayList<>();
        for(test tst:tList){
            testList.add(new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId()));
        }
        return testList;
    }

    @Override
    public sectionDto SearchSection(String column, String value) throws SQLException, ClassNotFoundException {
        section sec = SecDao.Search(column,value);
        return new sectionDto(sec.getSecId(),sec.getSecName(),sec.getConsultant());
    }

    @Override
    public testDto SearchTest(String column, String value) throws SQLException, ClassNotFoundException {
        test tst = TDao.Search(column,value);
        return new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId());
    }

    @Override
    public ArrayList<String> getInstructions(String id) throws SQLException, ClassNotFoundException {
        return InsDao.getInstructions(id);
    }
}
