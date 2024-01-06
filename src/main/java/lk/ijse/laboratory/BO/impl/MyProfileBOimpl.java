package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.MyProfileBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.userDAO;
import lk.ijse.laboratory.Dto.userDto;
import lk.ijse.laboratory.Entity.user;

import java.sql.SQLException;

public class MyProfileBOimpl implements MyProfileBO {
    userDAO dao = (userDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean UpdateUser(userDto dto) throws SQLException, ClassNotFoundException {
        return dao.Update(new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail()));
    }
}
