package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.VerificationBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.tempUserDAO;
import lk.ijse.laboratory.Dto.userDto;
import lk.ijse.laboratory.Entity.user;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class VerificationBOimpl implements VerificationBO {
    tempUserDAO dao = (tempUserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEMPUSER);
    @Override
    public boolean saveTempUser(userDto dto, Date dt, Time tm) throws SQLException, ClassNotFoundException {
        user entity =new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail());
        return dao.saveTempUser(entity,dt,tm);
    }
}
