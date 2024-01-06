package lk.ijse.laboratory.BO.custom;

import lk.ijse.laboratory.BO.SuperBO;
import lk.ijse.laboratory.Dto.machineDto;
import lk.ijse.laboratory.Dto.sectionDto;

import java.sql.SQLException;
import java.util.List;

public interface MachinesBO extends SuperBO {
    String generateNextMachineId() throws SQLException, ClassNotFoundException;
    List<machineDto> loadAllMachines() throws SQLException, ClassNotFoundException;
    List<sectionDto> loadAllSections() throws SQLException, ClassNotFoundException;
    boolean SaveMachine(machineDto dto) throws SQLException, ClassNotFoundException;
    machineDto SearchMachine(String code, String nic) throws SQLException, ClassNotFoundException;
    sectionDto SearchSection(String code, String nic) throws SQLException, ClassNotFoundException;
    boolean UpdateMachine(machineDto dto) throws SQLException, ClassNotFoundException;
    boolean DeleteMachine(String id) throws SQLException, ClassNotFoundException;
}
