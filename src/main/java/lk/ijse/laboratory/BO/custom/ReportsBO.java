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
    prescriptionDto SearchPrescription(String code, String nic) throws SQLException, ClassNotFoundException;
    patientDto SearchPatients(String code, String nic) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException;
    machineDto SearchMachines(String code, String nic) throws SQLException, ClassNotFoundException;
    sectionDto SearchSections(String code, String nic) throws SQLException, ClassNotFoundException;
    collectingCenterDto SearchCenters(String code, String nic) throws SQLException, ClassNotFoundException;
    resultDto SearchResults(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateReports(ptTestDetailsDto dto) throws SQLException, ClassNotFoundException;
}
