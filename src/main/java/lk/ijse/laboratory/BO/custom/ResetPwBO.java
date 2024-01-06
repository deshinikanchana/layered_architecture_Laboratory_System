package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

import java.sql.SQLException;

public interface ResetPwBO extends SuperBO {
    boolean Update(userDto dto) throws SQLException, ClassNotFoundException;
}
