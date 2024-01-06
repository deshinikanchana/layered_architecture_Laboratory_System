package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.testDto;
import lk.ijse.laboratory.Dto.sectionDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TestDetailsBO extends SuperBO {
    List<testDto> loadAllTest() throws SQLException, ClassNotFoundException;
    sectionDto SearchSection(String code, String nic) throws SQLException, ClassNotFoundException;
    testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException;
    ArrayList<String> getInstructions(String id) throws SQLException, ClassNotFoundException;
}
