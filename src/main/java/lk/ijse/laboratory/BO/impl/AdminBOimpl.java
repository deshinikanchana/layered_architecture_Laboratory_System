package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.AdminBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.*;

import java.sql.SQLException;

public class AdminBOimpl implements AdminBO {
    attendanceDAO atDao = (attendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    reportDAO reDao = (reportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REPORT);
    ordersDAO oDao = (ordersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    stockItemDAO iDao = (stockItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCKITEM);
    @Override
    public int getDailyEmployeeCount() throws SQLException, ClassNotFoundException {
        return atDao.getDailyCount();
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        return reDao.getCount();
    }

    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        return oDao.getOrderCount();
    }

    @Override
    public int warningStocks() throws SQLException, ClassNotFoundException {
        return iDao.warningStocks();
    }

    @Override
    public int getPendingReports() throws SQLException, ClassNotFoundException {
        return reDao.getPendingReports();
    }
}
