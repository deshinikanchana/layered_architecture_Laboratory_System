package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;

import java.sql.SQLException;

public interface UserDashBoardBO extends SuperBO {
    int getDailyCount() throws SQLException, ClassNotFoundException;
    int getPrescriptionCount() throws SQLException, ClassNotFoundException;
    int getReportCount() throws SQLException, ClassNotFoundException;
    int getPendingReports() throws SQLException, ClassNotFoundException;
}
