package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.stockItemDto;

import java.sql.SQLException;
import java.util.List;

public interface StocksBO extends SuperBO {
    String generateNextItemId() throws SQLException, ClassNotFoundException;
    List<stockItemDto> loadAllItems() throws SQLException, ClassNotFoundException;
    boolean SaveItem(stockItemDto dto) throws SQLException, ClassNotFoundException;
    stockItemDto SearchItem(String column, String value) throws SQLException, ClassNotFoundException;
    boolean UpdateItem(stockItemDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteItem(String id) throws SQLException, ClassNotFoundException;
}
