package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.userDto;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public interface VerificationBO extends SuperBO {
    boolean saveTempUser(userDto user, Date dt, Time tm) throws SQLException, ClassNotFoundException;
}
