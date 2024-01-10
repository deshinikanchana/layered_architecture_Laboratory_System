package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

import java.sql.SQLException;

public interface ForgotPasswordBO extends SuperBO {
    userDto SearchUser(String column, String value) throws SQLException, ClassNotFoundException;
}
