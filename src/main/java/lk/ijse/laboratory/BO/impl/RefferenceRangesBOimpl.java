package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.RefferenceRangesBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.refferenceRangeDAO;
import lk.ijse.laboratory.DAO.custom.subTestDAO;
import lk.ijse.laboratory.DAO.custom.testDAO;
import lk.ijse.laboratory.DAO.impl.refferenceRangeDAOimpl;
import lk.ijse.laboratory.DAO.impl.subTestDAOimpl;
import lk.ijse.laboratory.DAO.impl.testDAOimpl;
import lk.ijse.laboratory.Dto.refferenceRangesDto;
import lk.ijse.laboratory.Dto.subTestDto;
import lk.ijse.laboratory.Dto.testDto;
import lk.ijse.laboratory.Entity.refferenceRanges;
import lk.ijse.laboratory.Entity.subTest;
import lk.ijse.laboratory.Entity.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefferenceRangesBOimpl implements RefferenceRangesBO {
    subTestDAO subDao = (subTestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUBTEST);
    testDAO tDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);
    refferenceRangeDAO refDao  = (refferenceRangeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REFFERENCERANGES);
    @Override
    public String generateNextSubTestId() throws SQLException, ClassNotFoundException {
        return subDao.generateNextId();
    }

    @Override
    public List<subTestDto> loadAllSubTest() throws SQLException, ClassNotFoundException {
        List<subTest> sList = subDao.loadAll();
        List<subTestDto> subList = new ArrayList<>();
        for(subTest st:sList){
            subList.add(new subTestDto(st.getTestId(),st.getSubTestId(),st.getName()));
        }
        return subList;
    }

    @Override
    public List<testDto> loadAllTest() throws SQLException, ClassNotFoundException {
        List<test> tList = tDao.loadAll();
        List<testDto> testList = new ArrayList<>();
        for(test tst:tList){
            testList.add(new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId()));
        }
        return testList;
    }

    @Override
    public List<refferenceRangesDto> loadAllRefferenceRanges() throws SQLException, ClassNotFoundException {
        List<refferenceRanges> rList = refDao.loadAll();
        List<refferenceRangesDto> refList = new ArrayList<>();
        for(refferenceRanges ref:rList){
            refList.add(new refferenceRangesDto(ref.getSubTestId(),ref.getRanges(),ref.getUnit()));
        }
        return refList;
    }

    @Override
    public subTestDto SearchSubTest(String code, String nic) throws SQLException, ClassNotFoundException {
        subTest tst = subDao.Search(code,nic);
        return new subTestDto(tst.getTestId(),tst.getSubTestId(),tst.getName());
    }

    @Override
    public boolean SaveRefferenceRanges(refferenceRangesDto dto) throws SQLException, ClassNotFoundException {
        return refDao.Save(new refferenceRanges(dto.getSubTestId(),dto.getRanges(),dto.getUnit()));
    }

    @Override
    public boolean UpdateRefferenceRanges(refferenceRangesDto dto) throws SQLException, ClassNotFoundException {
        return refDao.Update(new refferenceRanges(dto.getSubTestId(),dto.getRanges(),dto.getUnit()));
    }

    @Override
    public boolean DeleteRefferenceRanges(String id) throws SQLException, ClassNotFoundException {
        return refDao.Delete(id);
    }
}
