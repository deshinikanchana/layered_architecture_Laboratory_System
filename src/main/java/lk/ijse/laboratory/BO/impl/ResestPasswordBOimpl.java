package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.ResetPwBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.userDAO;
import lk.ijse.laboratory.DAO.impl.userDAOimpl;
import lk.ijse.laboratory.Dto.userDto;
import lk.ijse.laboratory.Entity.user;

import java.sql.SQLException;

public class ResestPasswordBOimpl implements ResetPwBO {
    userDAO Dao = (userDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean Update(userDto dto) throws SQLException, ClassNotFoundException {
        return Dao.Update(new user(dto.getUserId(),dto.getUserName(),dto.getUserType(),dto.getPassword(),dto.getEmail()));
    }
}
