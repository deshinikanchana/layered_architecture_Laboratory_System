package lk.ijse.laboratory.BO.custom;

import java.sql.SQLException;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

public interface MyProfileBO extends SuperBO {
    boolean UpdateUser(userDto dto) throws SQLException, ClassNotFoundException;
}
