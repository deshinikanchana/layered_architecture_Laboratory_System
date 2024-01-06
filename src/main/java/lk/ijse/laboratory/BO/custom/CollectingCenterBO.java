package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.collectingCenterDto;

import java.sql.SQLException;
import java.util.List;

public interface CollectingCenterBO extends SuperBO {
    String generateNextCenterId() throws SQLException, ClassNotFoundException;
    List<collectingCenterDto> loadAllCenters() throws SQLException, ClassNotFoundException;
    int getSampleCount(String CcId) throws SQLException, ClassNotFoundException;
    boolean SaveCenter(collectingCenterDto dto) throws SQLException, ClassNotFoundException;
    collectingCenterDto SearchCenter(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateCenter(collectingCenterDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteCenter(String id) throws SQLException, ClassNotFoundException;
}
