package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.stockItemDto;
import lk.ijse.laboratory.Dto.stockUsageDto;
import lk.ijse.laboratory.Dto.testDto;

import java.sql.SQLException;
import java.util.List;

public interface StockUsageBO extends SuperBO {
    List<testDto> loadAllTests() throws SQLException, ClassNotFoundException;
    List<stockItemDto> loadAllItems() throws SQLException, ClassNotFoundException;
    boolean SaveUsage(stockUsageDto dto) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String column, String value) throws SQLException, ClassNotFoundException;
    stockItemDto SearchItem(String column, String value) throws SQLException, ClassNotFoundException;
}
