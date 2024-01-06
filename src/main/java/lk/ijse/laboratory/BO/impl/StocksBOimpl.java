package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.StocksBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.stockItemDAO;
import lk.ijse.laboratory.Dto.stockItemDto;
import lk.ijse.laboratory.Entity.stockItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StocksBOimpl implements StocksBO {
    stockItemDAO dao = (stockItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKITEM);
    @Override
    public String generateNextItemId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<stockItemDto> loadAllItems() throws SQLException, ClassNotFoundException {
        List<stockItem> iList = dao.loadAll();
        List<stockItemDto> itemList = new ArrayList<>();
        for(stockItem st:iList){
            itemList.add(new stockItemDto(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel()));
        }
        return itemList;
    }

    @Override
    public boolean SaveItem(stockItemDto st) throws SQLException, ClassNotFoundException {
        return dao.Save(new stockItem(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel()));
    }

    @Override
    public stockItemDto SearchItem(String code, String nic) throws SQLException, ClassNotFoundException {
        stockItem st = dao.Search(code,nic);
        return new stockItemDto(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel());
    }

    @Override
    public boolean UpdateItem(stockItemDto st) throws SQLException, ClassNotFoundException {
        return dao.Update(new stockItem(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel()));
    }

    @Override
    public boolean DeleteItem(String id) throws SQLException, ClassNotFoundException {
        return dao.Delete(id);
    }
}
