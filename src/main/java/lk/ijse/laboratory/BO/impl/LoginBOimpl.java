package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.LoginBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.tempUserDAO;
import lk.ijse.laboratory.DAO.custom.userDAO;
import lk.ijse.laboratory.Dto.userDto;
import lk.ijse.laboratory.Entity.user;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class LoginBOimpl implements LoginBO {
    userDAO uDao = (userDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    tempUserDAO tempDao = (tempUserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEMPUSER);
    @Override
    public List<userDto> searchNewUsers(Date today, Time just) throws SQLException, ClassNotFoundException {
        List<user> Ulist = tempDao.searchNewUsers(today,just);
        List<userDto> userList = new ArrayList<>();
        for(user dto : Ulist){
            userList.add(new userDto(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail()));
        }
        return userList;
    }

    @Override
    public boolean deleteTempUsers(userDto dto) throws SQLException, ClassNotFoundException {
        user entity = new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail());
        return tempDao.deleteTempUsers(entity);
    }

    @Override
    public String generateNextUserId() throws SQLException, ClassNotFoundException {
        return uDao.generateNextId();
    }

    @Override
    public boolean SaveUser(userDto dto) throws SQLException, ClassNotFoundException {
        user entity = new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail());
        return uDao.Save(entity);
    }

    @Override
    public userDto CheckCredential(userDto dto) throws SQLException, ClassNotFoundException {
        user entity = new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail());
        user User = uDao.CheckCredential(entity);
        userDto uDTO = new userDto(User.getUserId(),User.getUserName(),User.getUserType(),User.getPassword(),User.getEmail());
        return uDTO;
    }
}
