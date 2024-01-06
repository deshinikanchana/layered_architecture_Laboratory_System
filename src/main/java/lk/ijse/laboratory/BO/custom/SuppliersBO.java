package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.supplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SuppliersBO extends SuperBO {
    String generateNextSupplierId() throws SQLException, ClassNotFoundException;
    List<supplierDto> loadAllSuppliers() throws SQLException, ClassNotFoundException;
    List<String> loadcategories() throws SQLException, ClassNotFoundException;
    boolean SaveSupplier(supplierDto dto) throws SQLException, ClassNotFoundException;
    supplierDto SearchSupplier(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateSupplier(supplierDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteSupplier(String id) throws SQLException, ClassNotFoundException;
}
