package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.refferenceRangesDto;
import lk.ijse.laboratory.Dto.subTestDto;
import lk.ijse.laboratory.Dto.testDto;

import java.sql.SQLException;
import java.util.List;

public interface RefferenceRangesBO extends SuperBO {
    String generateNextSubTestId() throws SQLException, ClassNotFoundException;
    List<subTestDto> loadAllSubTest() throws SQLException, ClassNotFoundException;
    List<testDto> loadAllTest() throws SQLException, ClassNotFoundException;
    List<refferenceRangesDto> loadAllRefferenceRanges() throws SQLException, ClassNotFoundException;
    subTestDto SearchSubTest(String column, String value) throws SQLException, ClassNotFoundException;
    boolean SaveRefferenceRanges(refferenceRangesDto dto) throws SQLException, ClassNotFoundException;
    boolean UpdateRefferenceRanges(refferenceRangesDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteRefferenceRanges(String id) throws SQLException, ClassNotFoundException;
}
