package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.OrdersBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.*;
import lk.ijse.laboratory.DAO.impl.*;
import lk.ijse.laboratory.Dto.itemOrderDetailDto;
import lk.ijse.laboratory.Dto.stockItemDto;
import lk.ijse.laboratory.Dto.supplierDto;
import lk.ijse.laboratory.Entity.itemOrderDetail;
import lk.ijse.laboratory.Entity.stockItem;
import lk.ijse.laboratory.Entity.supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersBOimpl implements OrdersBO {
    stockItemDAO itemDao = (stockItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKITEM);
    supplierDAO supDao = (supplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    ordersDAO ordersDao = (ordersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    OrderSaveDAO OdDao = (OrderSaveDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERSAVE);

    @Override
    public List<stockItemDto> loadAllItems() throws SQLException, ClassNotFoundException {
        List<stockItem> iList = itemDao.loadAll();
        List<stockItemDto> itemList = new ArrayList<>();
        for(stockItem it: iList){
            itemList.add(new stockItemDto(it.getItemCode(),it.getUserId(),it.getDescription(),it.getQtyOnHand(),it.getCategory(),it.getWarningLevel()));
        }
        return itemList;
    }

    @Override
    public List<supplierDto> loadAllSuppliers() throws SQLException, ClassNotFoundException {
        List<supplier> sList =  supDao.loadAll();
        List<supplierDto> supList = new ArrayList<>();
        for(supplier spl:sList){
            supList.add(new supplierDto(spl.getSupId(), spl.getName(),spl.getTelNo(),spl.getEmail(),spl.getCategory()));
        }
        return supList;
    }

    @Override
    public String generateNextOrderId() throws SQLException, ClassNotFoundException {
        return ordersDao.generateNextId();
    }

    @Override
    public stockItemDto SearchItem(String column, String value) throws SQLException, ClassNotFoundException {
        stockItem st = itemDao.Search(column,value);
        return new stockItemDto(st.getItemCode(),st.getUserId(),st.getDescription(),st.getQtyOnHand(),st.getCategory(),st.getWarningLevel());
    }

    @Override
    public boolean SaveOrder(itemOrderDetailDto dto) throws SQLException, ClassNotFoundException {
        return OdDao.Save(new itemOrderDetail(dto.getOrderId(),dto.getSupplierId(),dto.getTmList()));
    }
}
