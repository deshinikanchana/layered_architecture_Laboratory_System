package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.employeeDto;
import lk.ijse.laboratory.Dto.instructionDto;
import lk.ijse.laboratory.Dto.testDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InstructionBO extends SuperBO {
    String generateNextInstructionId() throws SQLException, ClassNotFoundException;
    List<instructionDto> loadAllInstructions() throws SQLException, ClassNotFoundException;
    List<testDto> loadAllTests() throws SQLException, ClassNotFoundException;
    boolean SaveInstructions(instructionDto dto) throws SQLException, ClassNotFoundException;
    boolean UpdateInstructions(instructionDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteInstructions(String id) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String column, String value) throws SQLException, ClassNotFoundException;

}
