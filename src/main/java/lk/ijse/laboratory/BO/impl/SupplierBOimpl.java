package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.SuppliersBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.stockItemDAO;
import lk.ijse.laboratory.DAO.custom.supplierDAO;
import lk.ijse.laboratory.Dto.supplierDto;
import lk.ijse.laboratory.Entity.supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOimpl implements SuppliersBO {
    supplierDAO dao = (supplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    stockItemDAO IDao = (stockItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKITEM);
    @Override
    public String generateNextSupplierId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<supplierDto> loadAllSuppliers() throws SQLException, ClassNotFoundException {
        List<supplier> sList =  dao.loadAll();
        List<supplierDto> supList = new ArrayList<>();
        for(supplier sup:sList){
            supList.add(new supplierDto(sup.getSupId(),sup.getName(),sup.getTelNo(),sup.getEmail(),sup.getCategory()));
        }
        return supList;
    }

    @Override
    public List<String> loadcategories() throws SQLException, ClassNotFoundException {
        return IDao.loadcategories();
    }

    @Override
    public boolean SaveSupplier(supplierDto dto) throws SQLException, ClassNotFoundException {
        return dao.Save(new supplier(dto.getSupId(),dto.getName(),dto.getTelNo(),dto.getEmail(),dto.getCategory()));
    }

    @Override
    public supplierDto SearchSupplier(String column, String value) throws SQLException, ClassNotFoundException {
        supplier sup = dao.Search(column,value);
        return new supplierDto(sup.getSupId(),sup.getName(),sup.getTelNo(),sup.getEmail(),sup.getCategory());
    }

    @Override
    public boolean UpdateSupplier(supplierDto dto) throws SQLException, ClassNotFoundException {
        return dao.Update(new supplier(dto.getSupId(),dto.getName(),dto.getTelNo(),dto.getEmail(),dto.getCategory()));
    }

    @Override
    public boolean DeleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return dao.Delete(id);
    }
}
