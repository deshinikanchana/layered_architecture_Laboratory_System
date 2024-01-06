package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.ResultsBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.refferenceRangeDAO;
import lk.ijse.laboratory.DAO.custom.resultDAO;
import lk.ijse.laboratory.DAO.custom.subTestDAO;
import lk.ijse.laboratory.DAO.impl.refferenceRangeDAOimpl;
import lk.ijse.laboratory.DAO.impl.resultDAOimpl;
import lk.ijse.laboratory.DAO.impl.subTestDAOimpl;
import lk.ijse.laboratory.Dto.refferenceRangesDto;
import lk.ijse.laboratory.Dto.resultDto;
import lk.ijse.laboratory.Dto.subTestDto;
import lk.ijse.laboratory.Entity.refferenceRanges;
import lk.ijse.laboratory.Entity.result;
import lk.ijse.laboratory.Entity.subTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultBOimpl implements ResultsBO {
    resultDAO resDao = (resultDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESULT);
    subTestDAO subDao = (subTestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUBTEST);
    refferenceRangeDAO refDao = (refferenceRangeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REFFERENCERANGES);
    @Override
    public boolean SaveResult(resultDto dto) throws SQLException, ClassNotFoundException {
        return resDao.Save(new result(dto.getPresId(),dto.getSubTestId(),dto.getResult()));
    }

    @Override
    public subTestDto SearchSubTest(String code, String nic) throws SQLException, ClassNotFoundException {
        subTest st = subDao.Search(code,nic);
        return new subTestDto(st.getTestId(),st.getSubTestId(),st.getName());
    }

    @Override
    public resultDto SearchResults(String code, String nic) throws SQLException, ClassNotFoundException {
        result res = resDao.Search(code,nic);
        return new resultDto(res.getPresId(),res.getSubTestId(),res.getResult());
    }

    @Override
    public List<subTestDto> getAllsubTests(String text) throws SQLException, ClassNotFoundException {
        List<subTest> sList = subDao.loadAll();
        List<subTestDto> subList = new ArrayList<>();
        for(subTest st:sList){
            subList.add(new subTestDto(st.getTestId(),st.getSubTestId(),st.getName()));
        }
        return subList;
    }

    @Override
    public List<refferenceRangesDto> getRangeList(String subId) throws SQLException, ClassNotFoundException {
        List<refferenceRanges> rList = refDao.getRangeList(subId);
        List<refferenceRangesDto> refList = new ArrayList<>();
        for(refferenceRanges ref:rList){
            refList.add(new refferenceRangesDto(ref.getSubTestId(),ref.getRanges(),ref.getUnit()));
        }
        return refList;
    }
}
