package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.UserBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.userDAO;
import lk.ijse.laboratory.Dto.userDto;
import lk.ijse.laboratory.Entity.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOimpl implements UserBO {
    userDAO dao = (userDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public String generateNextUserId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<userDto> loadAllUsers() throws SQLException, ClassNotFoundException {
        List<user> uList = dao.loadAll();
        List<userDto> userList = new ArrayList<>();
        for(user us:uList){
            userList.add(new userDto(us.getUserId(),us.getUserName(),us.getUserType(),us.getPassword(),us.getEmail()));
        }
        return userList;
    }

    @Override
    public boolean SaveUser(userDto dto) throws SQLException, ClassNotFoundException {
        return dao.Save(new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail()));
    }

    @Override
    public userDto SearchUser(String code, String nic) throws SQLException, ClassNotFoundException {
        user us = dao.Search(code,nic);
        return new userDto(us.getUserId(),us.getUserName(),us.getUserType(),us.getPassword(),us.getEmail());
    }
}
