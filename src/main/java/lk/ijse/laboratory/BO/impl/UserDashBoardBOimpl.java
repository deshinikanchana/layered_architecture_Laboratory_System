package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.UserDashBoardBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.attendanceDAO;
import lk.ijse.laboratory.DAO.custom.prescriptionDAO;
import lk.ijse.laboratory.DAO.custom.reportDAO;
import java.sql.SQLException;

public class UserDashBoardBOimpl implements UserDashBoardBO {
    prescriptionDAO Prdao = (prescriptionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION);
    reportDAO ReDao = (reportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REPORT);
    attendanceDAO dao = (attendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    @Override
    public int getDailyCount() throws SQLException, ClassNotFoundException {
        return dao.getDailyCount();
    }

    @Override
    public int getPrescriptionCount() throws SQLException, ClassNotFoundException {
        return Prdao.getCount();
    }

    @Override
    public int getReportCount() throws SQLException, ClassNotFoundException {
        return ReDao.getCount();
    }

    @Override
    public int getPendingReports() throws SQLException, ClassNotFoundException {
        return ReDao.getPendingReports();
    }
}
