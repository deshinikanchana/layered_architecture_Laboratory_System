package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.designationDto;

import java.sql.SQLException;
import java.util.List;

public interface DesignationBO extends SuperBO {
    String generateNextDesignationId() throws SQLException, ClassNotFoundException;
    List<designationDto> loadAllDesignations() throws SQLException, ClassNotFoundException;
    int getEmpCount(String jobId) throws SQLException, ClassNotFoundException;
    boolean SaveDesignation(designationDto dto) throws SQLException, ClassNotFoundException;
    designationDto SearchDesignation(String column, String value) throws SQLException, ClassNotFoundException;
    boolean UpdateDesignation(designationDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteDesignation(String id) throws SQLException, ClassNotFoundException;
}
