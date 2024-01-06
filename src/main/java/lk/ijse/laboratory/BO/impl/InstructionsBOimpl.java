package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.InstructionBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.instructionDAO;
import lk.ijse.laboratory.DAO.custom.testDAO;
import lk.ijse.laboratory.Dto.instructionDto;
import lk.ijse.laboratory.Dto.testDto;
import lk.ijse.laboratory.Entity.instruction;
import lk.ijse.laboratory.Entity.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructionsBOimpl implements InstructionBO {
    instructionDAO dao = (instructionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INSTRUCTION);
    testDAO  TDao = (testDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TEST);
    @Override
    public String generateNextInstructionId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<instructionDto> loadAllInstructions() throws SQLException, ClassNotFoundException {
        List<instruction> Ilist = dao.loadAll();
        List<instructionDto> insList = new ArrayList<>();
        for(instruction i:Ilist){
            insList.add(new instructionDto(i.getNo(),i.getTestId(),i.getInstrucion()));
        }
        return insList;
    }

    @Override
    public List<testDto> loadAllTests() throws SQLException, ClassNotFoundException {
        List<test> tList =TDao.loadAll();
        List<testDto> testList = new ArrayList<>();
        for(test T:tList){
            testList.add(new testDto(T.getTestId(),T.getSecId(),T.getTest(),T.getEstimatedTime(),T.getPrice(),T.getSampleType(),T.getMachineId()));
        }
        return testList;
    }

    @Override
    public boolean SaveInstructions(instructionDto dto) throws SQLException, ClassNotFoundException {
        return dao.Save(new instruction(dto.getNo(),dto.getTestId(),dto.getInstrucion()));
    }

    @Override
    public boolean UpdateInstructions(instructionDto dto) throws SQLException, ClassNotFoundException {
        return dao.Update(new instruction(dto.getNo(),dto.getTestId(),dto.getInstrucion()));
    }

    @Override
    public boolean DeleteInstructions(String id) throws SQLException, ClassNotFoundException {
        return dao.Delete(id);
    }

    @Override
    public testDto SearchTest(String code, String nic) throws SQLException, ClassNotFoundException {
        test tst = TDao.Search(code,nic);
        return new testDto(tst.getTestId(),tst.getSecId(),tst.getTest(),tst.getEstimatedTime(),tst.getPrice(),tst.getSampleType(),tst.getMachineId());
    }

}
