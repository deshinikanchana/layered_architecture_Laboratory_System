package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.sectionDto;

import java.sql.SQLException;
import java.util.List;

public interface SectionBO extends SuperBO {
    String generateNextSectionId() throws SQLException, ClassNotFoundException;
    List<sectionDto> loadAllSections() throws SQLException, ClassNotFoundException;
    int getTestCount(String secId) throws SQLException, ClassNotFoundException;
    boolean SaveSection(sectionDto dto) throws SQLException, ClassNotFoundException;
    sectionDto SearchSection(String column, String value) throws SQLException, ClassNotFoundException;
    boolean UpdateSection(sectionDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteSection(String id) throws SQLException, ClassNotFoundException;
}
