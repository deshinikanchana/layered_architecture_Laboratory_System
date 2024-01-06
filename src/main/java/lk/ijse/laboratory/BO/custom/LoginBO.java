package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface LoginBO extends SuperBO {
    public List<userDto> searchNewUsers(Date today, Time just) throws SQLException, ClassNotFoundException;
    public boolean deleteTempUsers(userDto dto) throws SQLException, ClassNotFoundException;
    public String generateNextUserId() throws SQLException, ClassNotFoundException;
    public boolean SaveUser(userDto dto) throws SQLException, ClassNotFoundException;
    public userDto CheckCredential(userDto dtoUser) throws SQLException, ClassNotFoundException;

}
