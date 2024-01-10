package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.ForgotPasswordBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.userDAO;
import lk.ijse.laboratory.Dto.userDto;
import lk.ijse.laboratory.Entity.user;

import java.sql.SQLException;

public class ForgotPasswordBOimpl implements ForgotPasswordBO {
    userDAO dao = (userDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public userDto SearchUser(String column, String value) throws SQLException, ClassNotFoundException {
        user US = dao.Search(column,value);
        return new userDto(US.getUserId(),US.getUserName(), US.getUserType(),US.getPassword(),US.getEmail());
    }
}
