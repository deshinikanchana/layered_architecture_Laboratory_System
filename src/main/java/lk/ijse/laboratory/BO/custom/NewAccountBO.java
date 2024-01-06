package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

import java.sql.SQLException;

public interface NewAccountBO extends SuperBO {
    String generateNextUserId() throws SQLException, ClassNotFoundException;
    userDto getLastAdmin() throws SQLException, ClassNotFoundException;
}
