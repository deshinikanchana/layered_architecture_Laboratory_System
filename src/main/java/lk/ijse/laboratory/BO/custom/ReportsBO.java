package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.*;

import java.sql.SQLException;
import java.util.List;

public interface ReportsBO extends SuperBO {
    String generateNextId() throws SQLException, ClassNotFoundException;
    List<ptTestDetailsDto> loadAllReports() throws SQLException, ClassNotFoundException;
    List<prescriptionDto> loadAllPrescriptions() throws SQLException, ClassNotFoundException;
    List<ptTestDetailsDto> loadTestIds(String Id) throws SQLException, ClassNotFoundException;
    List<subTestDto> getAllsubTests(String text) throws SQLException, ClassNotFoundException;
    List<refferenceRangesDto> getRangeList(String subId) throws SQLException, ClassNotFoundException;
    prescriptionDto SearchPrescription(String column, String value) throws SQLException, ClassNotFoundException;
    patientDto SearchPatients(String column, String value) throws SQLException, ClassNotFoundException;
    ptTestDetailsDto SearchReports(String column, String value) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String column, String value) throws SQLException, ClassNotFoundException;
    machineDto SearchMachines(String column, String value) throws SQLException, ClassNotFoundException;
    sectionDto SearchSections(String column, String value) throws SQLException, ClassNotFoundException;
    collectingCenterDto SearchCenters(String column, String value) throws SQLException, ClassNotFoundException;
    resultDto SearchResults(String column, String value) throws SQLException, ClassNotFoundException;
    boolean UpdateReports(ptTestDetailsDto dto) throws SQLException, ClassNotFoundException;
}
