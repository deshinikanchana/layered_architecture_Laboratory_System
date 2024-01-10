package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.stockItemDto;
import lk.ijse.laboratory.Dto.supplierDto;
import lk.ijse.laboratory.Dto.itemOrderDetailDto;

import java.sql.SQLException;
import java.util.List;

public interface OrdersBO extends SuperBO {
    List<stockItemDto> loadAllItems() throws SQLException, ClassNotFoundException;
    List<supplierDto> loadAllSuppliers() throws SQLException, ClassNotFoundException;
    String generateNextOrderId() throws SQLException, ClassNotFoundException;
    stockItemDto SearchItem(String column, String value) throws SQLException, ClassNotFoundException;
    boolean SaveOrder(itemOrderDetailDto dto) throws SQLException, ClassNotFoundException;
}
