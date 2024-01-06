package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.StockUsageBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.stockItemDAO;
import lk.ijse.laboratory.DAO.custom.stockUsageDAO;
import lk.ijse.laboratory.DAO.custom.testDAO;
import lk.ijse.laboratory.Dto.stockItemDto;
import lk.ijse.laboratory.Dto.stockUsageDto;
import lk.ijse.laboratory.Dto.testDto;
import lk.ijse.laboratory.Entity.stockItem;
import lk.ijse.laboratory.Entity.stockUsage;
import lk.ijse.laboratory.Entity.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockUsageBOimpl implements StockUsageBO {
    stockItemDAO IDao = (stockItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKITEM);
    stockUsageDAO dao = (stockUsageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKUSAGE);
    testDAO TDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);

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
    public List<stockItemDto> loadAllItems() throws SQLException, ClassNotFoundException {
        List<stockItem> iList = IDao.loadAll();
        List<stockItemDto> itemList = new ArrayList<>();
        for(stockItem st:iList){
            itemList.add(new stockItemDto(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel()));
        }
        return itemList;
    }

    @Override
    public boolean SaveUsage(stockUsageDto dto) throws SQLException, ClassNotFoundException {
        return dao.Save(new stockUsage(dto.getTestId(),dto.getItemCode(),dto.getQtyPerTest()));
    }

    @Override
    public testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException {
        test tst = TDao.Search(code,nic);
        return new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId());
    }

    @Override
    public stockItemDto SearchItem(String code, String nic) throws SQLException, ClassNotFoundException {
        stockItem st =  IDao.Search(code,nic);
        return new stockItemDto(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel());
    }
}
