package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.collectingCenterDto;
import lk.ijse.laboratory.Dto.patientDto;

import java.sql.SQLException;
import java.util.List;

public interface PatientsBO extends SuperBO {
    String generateNextPatientId() throws SQLException, ClassNotFoundException;
    List<patientDto> loadAllPatients() throws SQLException, ClassNotFoundException;
    List<collectingCenterDto> loadAllCenters() throws SQLException, ClassNotFoundException;
    boolean SavePatient(patientDto dto) throws SQLException, ClassNotFoundException;
    patientDto SearchPatient(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdatePatient(patientDto dto) throws SQLException, ClassNotFoundException;
    boolean DeletePatient(String id) throws SQLException, ClassNotFoundException;
}
