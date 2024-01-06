package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    String generateNextUserId() throws SQLException, ClassNotFoundException;
    List<userDto> loadAllUsers() throws SQLException, ClassNotFoundException;
    boolean SaveUser(userDto dto) throws SQLException, ClassNotFoundException;
    userDto SearchUser(String code, String nic) throws SQLException, ClassNotFoundException;

}
