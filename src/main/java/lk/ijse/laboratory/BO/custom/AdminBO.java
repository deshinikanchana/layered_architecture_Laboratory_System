package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;

import java.sql.SQLException;

public interface AdminBO extends SuperBO {
    int getDailyEmployeeCount() throws SQLException, ClassNotFoundException;
    int getCount() throws SQLException, ClassNotFoundException;
    int getOrderCount() throws SQLException, ClassNotFoundException;
    int warningStocks() throws SQLException, ClassNotFoundException;
    int getPendingReports() throws SQLException, ClassNotFoundException;
}
