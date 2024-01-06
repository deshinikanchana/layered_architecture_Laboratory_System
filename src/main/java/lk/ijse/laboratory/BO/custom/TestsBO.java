package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.machineDto;
import lk.ijse.laboratory.Dto.sectionDto;
import lk.ijse.laboratory.Dto.testDto;

import java.sql.SQLException;
import java.util.List;

public interface TestsBO extends SuperBO {
    String generateNextTestId() throws SQLException, ClassNotFoundException;
    List<testDto> loadAllTests() throws SQLException, ClassNotFoundException;
    List<machineDto> loadAllMachines() throws SQLException, ClassNotFoundException;
    List<sectionDto> loadAllSections() throws SQLException, ClassNotFoundException;
    sectionDto SearchSection(String code, String nic) throws SQLException, ClassNotFoundException;
    machineDto SearchMachine(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean SaveTest(testDto dto) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateTest(testDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteTest(String id) throws SQLException, ClassNotFoundException;
}
