package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.*;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionsBO extends SuperBO {
    String generateNextPrescriptionId() throws SQLException, ClassNotFoundException;
    List<patientDto> loadAllPatients() throws SQLException, ClassNotFoundException;
    List<testDto> loadAllTests() throws SQLException, ClassNotFoundException;
    boolean SavePrescription(prescriptionDto dto) throws SQLException, ClassNotFoundException;
    boolean SaveReport(ptTestDetailsDto dto) throws SQLException, ClassNotFoundException;
    patientDto SearchPatient(String code, String nic) throws SQLException, ClassNotFoundException;
    collectingCenterDto SearchCenter(String code, String nic) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateStock(stockUsageDto dto) throws SQLException, ClassNotFoundException;
    prescriptionDto SearchPrescriptionById(String id) throws SQLException, ClassNotFoundException;
    List<stockUsageDto> getUsages(String testId) throws SQLException, ClassNotFoundException;

}
