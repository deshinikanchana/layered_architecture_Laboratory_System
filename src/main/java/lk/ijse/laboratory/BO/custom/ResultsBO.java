package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.refferenceRangesDto;
import lk.ijse.laboratory.Dto.resultDto;
import lk.ijse.laboratory.Dto.subTestDto;

import java.sql.SQLException;
import java.util.List;

public interface ResultsBO extends SuperBO {
    boolean SaveResult(resultDto dto) throws SQLException, ClassNotFoundException;
    subTestDto SearchSubTest(String column, String value) throws SQLException, ClassNotFoundException;
    resultDto SearchResults(String column, String value) throws SQLException, ClassNotFoundException;
    List<subTestDto> getAllsubTests(String text) throws SQLException, ClassNotFoundException;
    List<refferenceRangesDto> getRangeList(String subId) throws SQLException, ClassNotFoundException;
}
